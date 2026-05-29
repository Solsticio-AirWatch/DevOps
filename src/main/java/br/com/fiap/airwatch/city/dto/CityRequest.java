package br.com.fiap.airwatch.city.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record CityRequest(@NotNull Long countryId,@NotBlank @Size(max=150) String name,String state,
    @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") BigDecimal latitude,
    @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") BigDecimal longitude,
    BigDecimal altitudeM,@Min(0) Long population){}
