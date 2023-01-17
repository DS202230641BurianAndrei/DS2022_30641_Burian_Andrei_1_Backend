package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ro.tuc.ds2020.WebSockets.ThresholdMessage;
import ro.tuc.ds2020.entities.ChatMessage;

@Controller
    public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws")
    @SendTo("/topic/thresholdMessages")
    public ThresholdMessage sendThresholdMessage(Long deviceId, Long accountId, String message){
        return new ThresholdMessage(deviceId, accountId, message);
    }

    @MessageMapping("/private-message") // send from frontend to -> /app/private-message
    public ChatMessage receivePrivateMessage(@Payload ChatMessage message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/private", message);
        //frontend must listen to /user/username/private

        return message;
    }
}
