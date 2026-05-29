package br.com.fiap.airwatch.alertconfig.repository;
import br.com.fiap.airwatch.alertconfig.model.AlertConfig;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AlertConfigRepository extends JpaRepository<AlertConfig,Long> {
    Page<AlertConfig> findByUser_Id(Long userId,Pageable p);
    Page<AlertConfig> findByCity_Id(Long cityId,Pageable p);
    boolean existsByUser_IdAndCity_IdAndPollutantAndOperator(Long u,Long c,String pol,String op);
}
