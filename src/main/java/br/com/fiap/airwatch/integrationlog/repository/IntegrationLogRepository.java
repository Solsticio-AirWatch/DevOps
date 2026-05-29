package br.com.fiap.airwatch.integrationlog.repository;
import br.com.fiap.airwatch.integrationlog.model.IntegrationLog;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface IntegrationLogRepository extends JpaRepository<IntegrationLog,Long> {
    Page<IntegrationLog> findByApiNameOrderByRequestedAtDesc(String apiName,Pageable p);
    Page<IntegrationLog> findByResultOrderByRequestedAtDesc(String result,Pageable p);
    Page<IntegrationLog> findByCity_IdOrderByRequestedAtDesc(Long cityId,Pageable p);
    @Query("SELECT l FROM IntegrationLog l WHERE l.result<>'SUCCESS' ORDER BY l.requestedAt DESC")
    Page<IntegrationLog> findAllErrors(Pageable p);
}
