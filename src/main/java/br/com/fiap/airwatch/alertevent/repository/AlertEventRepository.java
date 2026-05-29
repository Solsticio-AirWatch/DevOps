package br.com.fiap.airwatch.alertevent.repository;
import br.com.fiap.airwatch.alertevent.model.AlertEvent;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface AlertEventRepository extends JpaRepository<AlertEvent,Long> {
    Page<AlertEvent> findByStatusOrderByEventAtDesc(String status,Pageable p);
    @Query("SELECT e FROM AlertEvent e WHERE e.alertConfig.city.id=:cityId ORDER BY e.eventAt DESC")
    Page<AlertEvent> findByCityId(Long cityId,Pageable p);
    @Query("SELECT e FROM AlertEvent e WHERE e.alertConfig.user.id=:userId ORDER BY e.eventAt DESC")
    Page<AlertEvent> findByUserId(Long userId,Pageable p);
}
