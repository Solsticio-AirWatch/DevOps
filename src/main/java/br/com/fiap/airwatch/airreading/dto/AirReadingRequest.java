package br.com.fiap.airwatch.airreading.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal; import java.time.LocalDateTime;
public record AirReadingRequest(
    @NotNull Long cityId, Long sensorId,
    BigDecimal pm25, BigDecimal pm10, BigDecimal co2, BigDecimal co,
    BigDecimal no2, BigDecimal so2, BigDecimal o3,
    BigDecimal temperature, BigDecimal humidity,
    Integer aqi, String category,
    @NotBlank String source, LocalDateTime readingAt) {}
