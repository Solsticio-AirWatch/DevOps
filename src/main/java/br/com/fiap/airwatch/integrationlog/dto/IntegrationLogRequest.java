package br.com.fiap.airwatch.integrationlog.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
public record IntegrationLogRequest(Long cityId,@NotBlank String apiName,@NotBlank String endpoint,
    String httpMethod,Integer httpStatus,Integer recordsCount,@NotBlank String result,
    String errorMessage,Integer responseMs,LocalDateTime requestedAt) {}
