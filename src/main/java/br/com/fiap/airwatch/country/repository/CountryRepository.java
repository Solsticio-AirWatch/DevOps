package br.com.fiap.airwatch.country.repository;
import br.com.fiap.airwatch.country.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface CountryRepository extends JpaRepository<Country,Long>{
    Optional<Country> findByIsoCode(String isoCode);
    boolean existsByIsoCode(String isoCode);
}
