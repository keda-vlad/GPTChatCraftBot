package tt.kvlad.tgpt.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tt.kvlad.tgpt.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
