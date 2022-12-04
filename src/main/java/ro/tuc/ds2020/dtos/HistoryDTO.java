package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class HistoryDTO {

    private Long id;

    @NotNull
    private Date timestamp;

    @NotNull
    private int measurement;

    @NotNull
    private Long deviceId;

    public HistoryDTO(){

    }

    public HistoryDTO(Long id, Date timestamp, int measurement, Long deviceId) {
        this.id = id;
        this.timestamp = timestamp;
        this.measurement = measurement;
        this.deviceId = deviceId;
    }

    public HistoryDTO(Date timestamp, int measurement, Long deviceId) {
        this.timestamp = timestamp;
        this.measurement = measurement;
        this.deviceId = deviceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getMeasurement() {
        return measurement;
    }

    public void setMeasurement(int measurement) {
        this.measurement = measurement;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
