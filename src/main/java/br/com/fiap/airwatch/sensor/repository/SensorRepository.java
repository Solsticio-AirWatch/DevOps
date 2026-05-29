package br.com.fiap.airwatch.sensor.repository;
import br.com.fiap.airwatch.sensor.model.Sensor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SensorRepository extends JpaRepository<Sensor,Long> {
    Page<Sensor> findByCity_Id(Long cityId,Pageable p);
    Page<Sensor> findByStatus(String status,Pageable p);
}
