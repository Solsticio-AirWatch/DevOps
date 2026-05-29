package br.com.fiap.airwatch.country.dto;
import br.com.fiap.airwatch.country.model.Country;
import java.time.LocalDateTime;
public record CountryResponse(Long id,String name,String isoCode,String continent,LocalDateTime createdAt){
    public static CountryResponse from(Country c){return new CountryResponse(c.getId(),c.getName(),c.getIsoCode(),c.getContinent(),c.getCreatedAt());}
}
