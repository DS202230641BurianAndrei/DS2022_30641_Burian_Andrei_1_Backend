package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.History;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findHistoryByDevice(Device d);

    List<History> findHistoryByDevice_Id(Long id);

    History findHistoryById(Long id);
}
