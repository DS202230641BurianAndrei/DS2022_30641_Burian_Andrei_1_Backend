package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceAccountDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceUpdateDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Accounts;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.AccountsRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsService.class);
    private final DeviceRepository deviceRepo;
    private final AccountsRepository accountsRepo;

    @Autowired
    public DeviceService(DeviceRepository deviceRepo, AccountsRepository accountsRepo){
        this.deviceRepo = deviceRepo;
        this.accountsRepo = accountsRepo;
    }

    public List<DeviceDTO> findAllDevices(){
        List<Device> devices = deviceRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return devices.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO findDeviceById(Long id){
        Optional<Device> optionalDevice = deviceRepo.findById(id);
        if ( !optionalDevice.isPresent() ){
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDTO(optionalDevice.get());
    }

    public Long insert(DeviceDTO deviceDTO){
        Long userId = deviceDTO.getUserId();
        Accounts assocAccount = accountsRepo.findAccountsById(userId);

//        if ( assocAccount == null){
//            LOGGER.error("Account with id {} does not exist in db, when trying to add device with maximum consumption per hour {}.",
//                    userId, deviceDTO.getMaxConsptPerHour());
//            throw new ResourceNotFoundException(Accounts.class.getSimpleName() + " with id: " + userId);
//        }

        Device newDevice = DeviceBuilder.toEntity(deviceDTO, assocAccount);
        newDevice = deviceRepo.save(newDevice);
        LOGGER.debug("Device with id {} and maximum consumption per hour {} was inserted in db",
                newDevice.getId(), newDevice.getMaxConsptPerHour());

        return newDevice.getId();
    }


    public void delete(Long id){
        deviceRepo.deleteById(id);
    }

//    public void updateDeviceAccount(DeviceAccountDTO dto){
//        Long deviceId = dto.getDeviceId();
//        Long accountId = dto.getAccountId();
//        Device device = deviceRepo.findDeviceById(deviceId);
//        LOGGER.error("deviceId=" + deviceId);
//        LOGGER.error("accountId=" + accountId);
//        Accounts accounts = accountsRepo.findAccountsById(accountId);
//        device.setAccounts(accounts);
//        deviceRepo.save(device);
//        LOGGER.debug("Device updated");
//    }

    public void update(DeviceUpdateDTO dto){
        Long deviceId = dto.getId();
        //device to be updated
        Device device = deviceRepo.findDeviceById(deviceId);

        //initialized with the current values
        //if no new value is provided the old one is preserved
        String newDescription = device.getDescription();
        String newAddress = device.getAddress();
        int newMaxConsptPerHour = device.getMaxConsptPerHour();
        Long newUserId = null;
        if(device.getAccounts() != null){
            newUserId = device.getAccounts().getId();
        }
        if (dto.getDescription() != null) {
            newDescription = dto.getDescription();
        }
        if (dto.getAddress() != null) {
            newAddress = dto.getAddress();
        }
        if (dto.getMaxConsptPerHour() != 0){
            newMaxConsptPerHour = dto.getMaxConsptPerHour();
        }
        if(dto.getUserId() != null){
            newUserId = dto.getUserId();
        }
        device.setDescription(newDescription);
        device.setAddress(newAddress);
        device.setMaxConsptPerHour(newMaxConsptPerHour);
        Accounts account = accountsRepo.findAccountsById(newUserId);
        device.setAccounts(account);

        deviceRepo.save(device);
        LOGGER.debug("Device updated");
    }
}
