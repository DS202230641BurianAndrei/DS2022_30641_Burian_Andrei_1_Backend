package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class HistoryUpdateDTO {
    @NotNull
    private Long id;

    private Date timestamp;

    private float measurement;

    private Long deviceId;

    public HistoryUpdateDTO(){

    }

    public HistoryUpdateDTO(Long id, Date timestamp, float measurement, Long deviceId) {
        this.id = id;
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

    public float getMeasurement() {
        return measurement;
    }

    public void setMeasurement(float measurement) {
        this.measurement = measurement;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
