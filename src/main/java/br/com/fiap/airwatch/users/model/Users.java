package br.com.fiap.airwatch.users.model;
import br.com.fiap.airwatch.city.model.City;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "USERS")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
    @SequenceGenerator(name = "seq_users", sequenceName = "SEQ_USERS", allocationSize = 1)
    @Column(name = "id_user")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_city")
    private City city;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "role", nullable = false, length = 20)
    @Builder.Default
    private String role = "CITIZEN";

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "is_active", nullable = false, length = 1)
    @Builder.Default
    private String isActive = "Y";

    @Column(name = "notify_email", nullable = false, length = 1)
    @Builder.Default
    private String notifyEmail = "Y";

    @Column(name = "notify_push", nullable = false, length = 1)
    @Builder.Default
    private String notifyPush = "N";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null)   createdAt   = LocalDateTime.now();
        if (isActive == null)    isActive    = "Y";
        if (notifyEmail == null) notifyEmail = "Y";
        if (notifyPush == null)  notifyPush  = "N";
        if (role == null)        role        = "CITIZEN";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
    @Override public String  getPassword()             { return passwordHash; }
    @Override public String  getUsername()             { return email; }
    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return "Y".equals(isActive); }
}
