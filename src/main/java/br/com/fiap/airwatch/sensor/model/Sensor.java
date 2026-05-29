package br.com.fiap.airwatch.sensor.model;
import br.com.fiap.airwatch.city.model.City;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "SENSOR")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sensor")
    @SequenceGenerator(name = "seq_sensor", sequenceName = "SEQ_SENSOR", allocationSize = 1)
    @Column(name = "id_sensor")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_city", nullable = false)
    private City city;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "location", length = 200)
    private String location;

    @Column(name = "latitude", precision = 10, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 6)
    private BigDecimal longitude;

    @Column(name = "source", nullable = false, length = 30)
    @Builder.Default
    private String source = "IOT";

    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private String status = "ACTIVE";

    @Column(name = "installed_at", nullable = false, updatable = false)
    private LocalDateTime installedAt;

    @Column(name = "last_reading_at")
    private LocalDateTime lastReadingAt;

    @PrePersist
    public void prePersist() {
        if (installedAt == null) installedAt = LocalDateTime.now();
    }
}
