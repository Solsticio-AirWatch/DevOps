package br.com.fiap.airwatch.airreading.model;
import br.com.fiap.airwatch.city.model.City;
import br.com.fiap.airwatch.sensor.model.Sensor;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "AIR_READING")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AirReading {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_air_reading")
    @SequenceGenerator(name = "seq_air_reading", sequenceName = "SEQ_AIR_READING", allocationSize = 1)
    @Column(name = "id_reading")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_city", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sensor")
    private Sensor sensor;

    @Column(name = "pm25",  precision = 8, scale = 3) private BigDecimal pm25;
    @Column(name = "pm10",  precision = 8, scale = 3) private BigDecimal pm10;
    @Column(name = "co2",   precision = 8, scale = 3) private BigDecimal co2;
    @Column(name = "co",    precision = 8, scale = 3) private BigDecimal co;
    @Column(name = "no2",   precision = 8, scale = 3) private BigDecimal no2;
    @Column(name = "so2",   precision = 8, scale = 3) private BigDecimal so2;
    @Column(name = "o3",    precision = 8, scale = 3) private BigDecimal o3;
    @Column(name = "temperature", precision = 6, scale = 2) private BigDecimal temperature;
    @Column(name = "humidity",    precision = 5, scale = 2) private BigDecimal humidity;
    @Column(name = "aqi")         private Integer aqi;
    @Column(name = "category",    length = 20)  private String category;
    @Column(name = "source",      nullable = false, length = 30) private String source;
    @Column(name = "reading_at",  nullable = false) private LocalDateTime readingAt;

    @PrePersist
    public void prePersist() {
        if (readingAt == null) readingAt = LocalDateTime.now();
        if (category == null && aqi != null) category = resolveCategory(aqi);
    }

    private String resolveCategory(int v) {
        if (v <= 50)  return "GOOD";
        if (v <= 100) return "MODERATE";
        if (v <= 150) return "BAD";
        if (v <= 200) return "VERY_BAD";
        return "HAZARDOUS";
    }
}
