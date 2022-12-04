package ro.tuc.ds2020.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class History implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "history_sequence")
    @SequenceGenerator(name = "history_sequence", sequenceName = "history_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @Column(name = "measurement", nullable = false)
    private int measurement;

    @ManyToOne
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private Device device;

    public History(){

    }

    public History(Long id, Date timestamp, int measurement, Device device) {
        this.id = id;
        this.timestamp = timestamp;
        this.measurement = measurement;
        this.device = device;
    }

    public History(Date timestamp, int measurement, Device device) {
        this.timestamp = timestamp;
        this.measurement = measurement;
        this.device = device;
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

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    public int getMeasurement() {
        return measurement;
    }

    public void setMeasurement(int measurement) {
        this.measurement = measurement;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
