package br.com.fiap.airwatch.users.repository;
import br.com.fiap.airwatch.users.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);
}
