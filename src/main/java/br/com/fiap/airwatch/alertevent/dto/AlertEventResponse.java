package br.com.fiap.airwatch.alertevent.dto;
import br.com.fiap.airwatch.alertevent.model.AlertEvent;
import java.math.BigDecimal; import java.time.LocalDateTime;
public record AlertEventResponse(Long id,Long alertConfigId,String pollutant,String severity,
    Long cityId,String cityName,Long readingId,BigDecimal measuredValue,BigDecimal threshold,
    String message,String status,String notificationSent,LocalDateTime eventAt,LocalDateTime notifiedAt) {
    public static AlertEventResponse from(AlertEvent e) {
        return new AlertEventResponse(e.getId(),e.getAlertConfig().getId(),
            e.getAlertConfig().getPollutant(),e.getAlertConfig().getSeverity(),
            e.getAlertConfig().getCity().getId(),e.getAlertConfig().getCity().getName(),
            e.getReading().getId(),e.getMeasuredValue(),e.getAlertConfig().getThreshold(),
            e.getMessage(),e.getStatus(),e.getNotificationSent(),e.getEventAt(),e.getNotifiedAt());
    }
}
