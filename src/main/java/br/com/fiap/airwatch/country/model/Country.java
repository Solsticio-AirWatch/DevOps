package br.com.fiap.airwatch.country.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "COUNTRY")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_country")
    @SequenceGenerator(name = "seq_country", sequenceName = "SEQ_COUNTRY", allocationSize = 1)
    @Column(name = "id_country")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "iso_code", nullable = false, length = 2, unique = true)
    private String isoCode;

    @Column(name = "continent", length = 50)
    private String continent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
