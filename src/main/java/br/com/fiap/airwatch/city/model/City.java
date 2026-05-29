package br.com.fiap.airwatch.city.model;
import br.com.fiap.airwatch.country.model.Country;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CITY")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_city")
    @SequenceGenerator(name = "seq_city", sequenceName = "SEQ_CITY", allocationSize = 1)
    @Column(name = "id_city")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country", nullable = false)
    private Country country;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 10, scale = 6)
    private BigDecimal longitude;

    @Column(name = "altitude_m", precision = 8, scale = 2)
    private BigDecimal altitudeM;

    @Column(name = "population")
    private Long population;

    @Column(name = "status", nullable = false, length = 1)
    @Builder.Default
    private String status = "A";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
