package br.com.fiap.airwatch.alertevent.model;
import br.com.fiap.airwatch.airreading.model.AirReading;
import br.com.fiap.airwatch.alertconfig.model.AlertConfig;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERT_EVENT")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AlertEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alert_event")
    @SequenceGenerator(name = "seq_alert_event", sequenceName = "SEQ_ALERT_EVENT", allocationSize = 1)
    @Column(name = "id_event")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alert_config", nullable = false)
    private AlertConfig alertConfig;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reading", nullable = false)
    private AirReading reading;

    @Column(name = "measured_value", nullable = false, precision = 8, scale = 3)
    private BigDecimal measuredValue;

    @Column(name = "message", length = 500) private String message;

    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default private String status = "PENDING";

    @Column(name = "notification_sent", nullable = false, length = 1)
    @Builder.Default private String notificationSent = "N";

    @Column(name = "event_at", nullable = false, updatable = false)
    private LocalDateTime eventAt;

    @Column(name = "notified_at") private LocalDateTime notifiedAt;

    @PrePersist
    public void prePersist() { if (eventAt == null) eventAt = LocalDateTime.now(); }
}
