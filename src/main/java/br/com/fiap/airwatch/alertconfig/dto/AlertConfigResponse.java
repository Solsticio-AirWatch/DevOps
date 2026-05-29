package br.com.fiap.airwatch.alertconfig.dto;
import br.com.fiap.airwatch.alertconfig.model.AlertConfig;
import java.math.BigDecimal; import java.time.LocalDateTime;
public record AlertConfigResponse(Long id,Long userId,String userName,Long cityId,String cityName,
    String pollutant,BigDecimal threshold,String operator,String severity,String isActive,LocalDateTime createdAt) {
    public static AlertConfigResponse from(AlertConfig a) {
        return new AlertConfigResponse(a.getId(),a.getUser().getId(),a.getUser().getName(),
            a.getCity().getId(),a.getCity().getName(),a.getPollutant(),a.getThreshold(),
            a.getOperator(),a.getSeverity(),a.getIsActive(),a.getCreatedAt());
    }
}
