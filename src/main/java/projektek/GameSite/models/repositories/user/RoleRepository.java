package projektek.GameSite.models.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import projektek.GameSite.models.data.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAuthority(String authority);
}
