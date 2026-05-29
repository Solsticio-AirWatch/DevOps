package br.com.fiap.airwatch.config;
import br.com.fiap.airwatch.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.*;
@Configuration @RequiredArgsConstructor
public class UserDetailsConfig {
    private final UsersRepository repo;
    @Bean public UserDetailsService userDetailsService() {
        return email->repo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found: "+email));
    }
}
