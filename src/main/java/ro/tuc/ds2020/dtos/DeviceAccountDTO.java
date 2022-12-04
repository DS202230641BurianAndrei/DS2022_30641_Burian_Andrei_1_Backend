package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DeviceAccountDTO {
    @NotNull
    private long deviceId;
    @NotNull
    private long accountId;

    public DeviceAccountDTO(){

    }

    public DeviceAccountDTO(long deviceId, long accountId) {
        this.deviceId = deviceId;
        this.accountId = accountId;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public long getAccountId() {
        return accountId;
    }

    @Override
    public int hashCode(){
        return Objects.hash(deviceId, accountId);
    }
}
