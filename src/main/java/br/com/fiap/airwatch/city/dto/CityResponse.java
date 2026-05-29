package br.com.fiap.airwatch.city.dto;
import br.com.fiap.airwatch.city.model.City;
import java.math.BigDecimal; import java.time.LocalDateTime;
public record CityResponse(Long id,Long countryId,String countryName,String isoCode,String name,String state,
    BigDecimal latitude,BigDecimal longitude,BigDecimal altitudeM,Long population,String status,LocalDateTime createdAt){
    public static CityResponse from(City c){
        return new CityResponse(c.getId(),c.getCountry().getId(),c.getCountry().getName(),c.getCountry().getIsoCode(),
            c.getName(),c.getState(),c.getLatitude(),c.getLongitude(),c.getAltitudeM(),c.getPopulation(),c.getStatus(),c.getCreatedAt());
    }
}
