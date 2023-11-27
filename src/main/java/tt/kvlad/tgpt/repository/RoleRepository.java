package tt.kvlad.tgpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tt.kvlad.tgpt.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(Role.RoleName roleName);
}
