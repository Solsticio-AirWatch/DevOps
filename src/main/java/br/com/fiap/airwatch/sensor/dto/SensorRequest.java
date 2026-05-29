package br.com.fiap.airwatch.sensor.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record SensorRequest(@NotNull Long cityId,@NotBlank @Size(max=100) String name,
    @NotBlank @Size(max=50) String type,String location,BigDecimal latitude,BigDecimal longitude,String source,String status) {}
