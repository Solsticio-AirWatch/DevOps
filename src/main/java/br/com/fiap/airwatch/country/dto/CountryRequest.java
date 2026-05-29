package br.com.fiap.airwatch.country.dto;
import jakarta.validation.constraints.*;
public record CountryRequest(@NotBlank @Size(max=100) String name,@NotBlank @Size(min=2,max=2) String isoCode,String continent){}
