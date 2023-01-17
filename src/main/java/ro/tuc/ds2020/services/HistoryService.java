package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.HistoryDTO;
import ro.tuc.ds2020.dtos.HistoryUpdateDTO;
import ro.tuc.ds2020.dtos.builders.HistoryBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.History;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.HistoryRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsService.class);
    private final HistoryRepository historyRepo;
    private final DeviceRepository deviceRepo;

    @Autowired
    public HistoryService(HistoryRepository historyRepo, DeviceRepository deviceRepo){
        this.historyRepo = historyRepo;
        this.deviceRepo = deviceRepo;
    }

    public List<HistoryDTO> findAllHistory(){
        List<History> history = historyRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return history.stream()
                .map(HistoryBuilder::toHistoryDTO)
                .collect(Collectors.toList());
    }

    public List<HistoryDTO> findDeviceHistory(Long deviceId){
        List<History> history = historyRepo.findHistoryByDevice_Id(deviceId);

        if (history.isEmpty()){
            throw new RuntimeException("History not found!");
        }

        return history.stream()
                .map(HistoryBuilder::toHistoryDTO)
                .collect(Collectors.toList());
    }

    public Long insert(HistoryDTO historyDTO){
        Long deviceId = historyDTO.getDeviceId();
        Device assocDevice = deviceRepo.findDeviceById(deviceId);

        if(assocDevice == null){
            LOGGER.error("Device with id {} does not exist in db, when trying to add history at timestamp {}.",
                    deviceId, historyDTO.getTimestamp());
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceId);
        }

        History newHistory = HistoryBuilder.toEntity(historyDTO, assocDevice);
        newHistory = historyRepo.save(newHistory);
        LOGGER.debug("History with id {}, timestamp {} and device id {} has been successfully added!",
                newHistory.getId(), newHistory.getTimestamp(), newHistory.getDevice().getId());

        return newHistory.getId();
    }


    public void delete(Long id){
        historyRepo.deleteById(id);
    }

    public void update(HistoryUpdateDTO dto){
        //id fo the account to be updated
        Long id = dto.getId();

        //history object to be updated
        History history = historyRepo.findHistoryById(id);

        //initialized with the current values
        //if no new value is provided the old one is preserved
        Date newTimestamp = history.getTimestamp();
        float newMeasurement = history.getMeasurement();
        Long newDeviceId = null;
        if(history.getDevice() != null){
            newDeviceId = history.getDevice().getId();
        }
        if(dto.getTimestamp() != null){
            newTimestamp = dto.getTimestamp();
        }
        if(dto.getMeasurement() != 0){
            newMeasurement = dto.getMeasurement();
        }
        if(dto.getDeviceId() != null){
            newDeviceId = dto.getDeviceId();
        }
        history.setTimestamp(newTimestamp);
        history.setMeasurement(newMeasurement);
        Device device = deviceRepo.findDeviceById(newDeviceId);
        history.setDevice(device);

        historyRepo.save(history);
        LOGGER.debug("History updated");
    }
}
