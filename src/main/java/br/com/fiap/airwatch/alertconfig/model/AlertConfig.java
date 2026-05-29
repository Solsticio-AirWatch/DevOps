package br.com.fiap.airwatch.alertconfig.model;
import br.com.fiap.airwatch.city.model.City;
import br.com.fiap.airwatch.users.model.Users;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERT_CONFIG")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AlertConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alert_config")
    @SequenceGenerator(name = "seq_alert_config", sequenceName = "SEQ_ALERT_CONFIG", allocationSize = 1)
    @Column(name = "id_alert_config")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_city", nullable = false)
    private City city;

    @Column(name = "pollutant", nullable = false, length = 20) private String pollutant;
    @Column(name = "threshold", nullable = false, precision = 8, scale = 3) private BigDecimal threshold;

    @Column(name = "operator", nullable = false, length = 2)
    @Builder.Default private String operator = ">=";

    @Column(name = "severity", nullable = false, length = 20)
    @Builder.Default private String severity = "WARNING";

    @Column(name = "is_active", nullable = false, length = 1)
    @Builder.Default private String isActive = "Y";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist public void prePersist() { if (createdAt == null) createdAt = LocalDateTime.now(); }
    @PreUpdate  public void preUpdate()  { updatedAt = LocalDateTime.now(); }
}
