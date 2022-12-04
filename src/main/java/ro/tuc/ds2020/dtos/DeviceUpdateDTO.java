package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;

public class DeviceUpdateDTO {

    @NotNull
    private Long id;

    private String description;

    private String address;

    private int maxConsptPerHour;

    private Long userId;

    public DeviceUpdateDTO(){

    }

    public DeviceUpdateDTO(Long id, String description, String address, int maxConsptPerHour, Long userId) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.maxConsptPerHour = maxConsptPerHour;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaxConsptPerHour() {
        return maxConsptPerHour;
    }

    public void setMaxConsptPerHour(int maxConsptPerHour) {
        this.maxConsptPerHour = maxConsptPerHour;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
