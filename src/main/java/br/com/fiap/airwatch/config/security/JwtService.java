package br.com.fiap.airwatch.config.security;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*; import java.util.function.Function;
@Service
public class JwtService {
    @Value("${airwatch.jwt.secret}") private String secret;
    @Value("${airwatch.jwt.expiration}") private long expiration;
    public String generateToken(UserDetails u) {
        return Jwts.builder().subject(u.getUsername()).issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis()+expiration)).signWith(key()).compact();
    }
    public boolean isValid(String t,UserDetails u) { return extractUsername(t).equals(u.getUsername())&&!isExpired(t); }
    public String extractUsername(String t) { return claim(t,Claims::getSubject); }
    private boolean isExpired(String t) { return claim(t,Claims::getExpiration).before(new Date()); }
    private <T> T claim(String t,Function<Claims,T> r) { return r.apply(Jwts.parser().verifyWith(key()).build().parseSignedClaims(t).getPayload()); }
    private SecretKey key() { return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); }
}
