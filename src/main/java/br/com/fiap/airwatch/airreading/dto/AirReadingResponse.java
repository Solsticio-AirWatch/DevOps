package br.com.fiap.airwatch.airreading.dto;
import br.com.fiap.airwatch.airreading.model.AirReading;
import java.math.BigDecimal; import java.time.LocalDateTime;
public record AirReadingResponse(Long id,Long cityId,String cityName,Long sensorId,String sensorName,
    BigDecimal pm25,BigDecimal pm10,BigDecimal co2,BigDecimal co,BigDecimal no2,BigDecimal so2,BigDecimal o3,
    BigDecimal temperature,BigDecimal humidity,Integer aqi,String category,String source,LocalDateTime readingAt) {
    public static AirReadingResponse from(AirReading r) {
        return new AirReadingResponse(r.getId(),r.getCity().getId(),r.getCity().getName(),
            r.getSensor()!=null?r.getSensor().getId():null,r.getSensor()!=null?r.getSensor().getName():null,
            r.getPm25(),r.getPm10(),r.getCo2(),r.getCo(),r.getNo2(),r.getSo2(),r.getO3(),
            r.getTemperature(),r.getHumidity(),r.getAqi(),r.getCategory(),r.getSource(),r.getReadingAt());
    }
}
