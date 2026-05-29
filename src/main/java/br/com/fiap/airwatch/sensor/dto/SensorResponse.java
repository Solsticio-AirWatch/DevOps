package br.com.fiap.airwatch.sensor.dto;
import br.com.fiap.airwatch.sensor.model.Sensor;
import java.math.BigDecimal; import java.time.LocalDateTime;
public record SensorResponse(Long id,Long cityId,String cityName,String name,String type,String location,
    BigDecimal latitude,BigDecimal longitude,String source,String status,LocalDateTime installedAt,LocalDateTime lastReadingAt) {
    public static SensorResponse from(Sensor s) {
        return new SensorResponse(s.getId(),s.getCity().getId(),s.getCity().getName(),s.getName(),s.getType(),
            s.getLocation(),s.getLatitude(),s.getLongitude(),s.getSource(),s.getStatus(),s.getInstalledAt(),s.getLastReadingAt());
    }
}
