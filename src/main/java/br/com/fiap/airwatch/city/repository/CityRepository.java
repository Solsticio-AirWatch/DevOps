package br.com.fiap.airwatch.city.repository;
import br.com.fiap.airwatch.city.model.City;
import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CityRepository extends JpaRepository<City,Long>{
    Page<City> findByCountry_Id(Long countryId,Pageable p);
}
