package ro.tuc.ds2020.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.controllers.WebSocketController;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.HistoryDTO;
import ro.tuc.ds2020.entities.ChatMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class RabbitMQService {
    private final HistoryService historyService;
    private final DeviceService deviceService;
    private final WebSocketController webSocketController;
    private final SimpMessagingTemplate template;

    private int currentHour = -1;
    private float hourlyConsumption = 0;
    private boolean thresholdExceeded = false;

    @RabbitListener(queues = "measurement")
    public void consume(String message){
        Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        HistoryDTO historyDTO = g.fromJson(message, HistoryDTO.class);

        /**
         * to be uncommented
         */
        //insert the object into the DB
        //historyService.insert(historyDTO);

        //read the hour from the date
        DateFormat dateFormat = new SimpleDateFormat("HH");
        int readHour = Integer.parseInt(dateFormat.format(historyDTO.getTimestamp()));

        //find the device associated with the measurement so that we can retrieve it maximum consumption
        DeviceDTO device = deviceService.findDeviceById(historyDTO.getDeviceId());

        //get the maximum consumption figure
        int maxConspt = device.getMaxConsptPerHour();

        /**
         * to be deleted
         */
        Long accountId = deviceService.findDeviceById(historyDTO.getDeviceId()).getUserId();
        //webSocketController.sendThresholdMessage(device.getId(), accountId, "History received!");
        //System.out.println("History received!");
        //this.template.convertAndSend("/topic/thresholdMessages", "History received!");

        if(currentHour == -1){
            currentHour = readHour;
            hourlyConsumption = 0;
        }else if (readHour > currentHour){
            hourlyConsumption = 0;
            thresholdExceeded = false;
        }else{
            hourlyConsumption += historyDTO.getMeasurement();
        }

//        this.template.convertAndSendToUser("buri", "/private", new ChatMessage("admin", "buri",
//                "Message 1", "15.01.2023 23:53"));
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        this.template.convertAndSendToUser("buri", "/private", new ChatMessage("admin", "buri",
//                "Message 2", "15.01.2023 23:53"));
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        this.template.convertAndSendToUser("buri", "/private", new ChatMessage("admin", "buri",
//                "Message 3", "15.01.2023 23:53"));

        if(hourlyConsumption > deviceService.findDeviceById(historyDTO.getDeviceId()).getMaxConsptPerHour() &&
            !thresholdExceeded){
            //Long accountId = deviceService.findDeviceById(historyDTO.getDeviceId()).getUserId();
            //webSocketController.sendThresholdMessage(device.getId(), accountId, "Threshold exceeded!");
            this.template.convertAndSend("/topic/thresholdMessages", "Threshold exceeded for device " +
                    device.getId() + "!");
            System.out.println("Threshold exceeded!");
            thresholdExceeded = true;


        }

    }

    public RabbitMQService(HistoryService historyService, DeviceService deviceService, WebSocketController webSocketController,
                           SimpMessagingTemplate template) {
        this.historyService = historyService;
        this.deviceService = deviceService;
        this.webSocketController = webSocketController;
        this.template = template;
    }
}
