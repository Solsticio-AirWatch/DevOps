package br.com.fiap.airwatch.airreading.repository;
import br.com.fiap.airwatch.airreading.model.AirReading;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface AirReadingRepository extends JpaRepository<AirReading,Long> {
    Page<AirReading> findByCity_IdOrderByReadingAtDesc(Long cityId,Pageable p);
    Page<AirReading> findBySensor_IdOrderByReadingAtDesc(Long sensorId,Pageable p);
    @Query("SELECT r FROM AirReading r WHERE r.city.id=:cityId ORDER BY r.readingAt DESC")
    Page<AirReading> findByCityId(Long cityId,Pageable p);
}
