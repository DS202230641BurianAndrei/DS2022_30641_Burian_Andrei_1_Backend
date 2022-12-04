package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.Device;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findDevicesByAccountsIsNull();

    List<Device> findDeviceByAccounts_Username(String username);

    Device findDeviceById(Long id);
}
