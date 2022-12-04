package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.HistoryDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.History;

public class HistoryBuilder {

    private HistoryBuilder(){

    }

    public static HistoryDTO toHistoryDTO(History history){
        return new HistoryDTO(history.getId(), history.getTimestamp(), history.getMeasurement(), history.getDevice().getId());
    }

    public static History toEntity(HistoryDTO historyDTO, Device device){
        return new History(historyDTO.getId(), historyDTO.getTimestamp(), historyDTO.getMeasurement(), device);
    }
}
