package br.com.fiap.airwatch.alertevent.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record AlertEventRequest(@NotNull Long alertConfigId,@NotNull Long readingId,
    @NotNull BigDecimal measuredValue,String message,String status) {}
