package ro.tuc.ds2020.WebSockets;

public class ThresholdMessage {
    private Long deviceId;
    private Long accountId;
    private String message;

    public ThresholdMessage(Long deviceId, Long accountId, String message) {
        this.deviceId = deviceId;
        this.accountId = accountId;
        this.message = message;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
