package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Accounts;
import ro.tuc.ds2020.entities.Device;

public class DeviceBuilder {

    private DeviceBuilder(){

    }

    public static DeviceDTO toDeviceDTO(Device device){
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(), device.getMaxConsptPerHour(),
                (device.getAccounts() == null) ? null : device.getAccounts().getId());
    }

    public static Device toEntity(DeviceDTO deviceDTO, Accounts account){
        return new Device(deviceDTO.getId(), deviceDTO.getDescription(), deviceDTO.getAddress(),
                deviceDTO.getMaxConsptPerHour(), account);
    }
}
