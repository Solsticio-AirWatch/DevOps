package br.com.fiap.airwatch.integrationlog.model;
import br.com.fiap.airwatch.city.model.City;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "INTEGRATION_LOG")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class IntegrationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_integration_log")
    @SequenceGenerator(name = "seq_integration_log", sequenceName = "SEQ_INTEGRATION_LOG", allocationSize = 1)
    @Column(name = "id_log")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_city")
    private City city;

    @Column(name = "api_name",     nullable = false, length = 50)  private String apiName;
    @Column(name = "endpoint",     nullable = false, length = 500) private String endpoint;
    @Column(name = "http_method",  nullable = false, length = 6)
    @Builder.Default private String httpMethod = "GET";
    @Column(name = "http_status")    private Integer httpStatus;
    @Column(name = "records_count")  private Integer recordsCount;
    @Column(name = "result",         nullable = false, length = 20) private String result;
    @Column(name = "error_message",  length = 1000) private String errorMessage;
    @Column(name = "requested_at",   nullable = false, updatable = false) private LocalDateTime requestedAt;
    @Column(name = "response_ms")    private Integer responseMs;

    @PrePersist
    public void prePersist() { if (requestedAt == null) requestedAt = LocalDateTime.now(); }
}
