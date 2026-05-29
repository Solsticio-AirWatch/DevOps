package br.com.fiap.airwatch.alertconfig.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record AlertConfigRequest(@NotNull Long userId,@NotNull Long cityId,
    @NotBlank String pollutant,@NotNull @DecimalMin("0.001") BigDecimal threshold,String operator,String severity) {}
