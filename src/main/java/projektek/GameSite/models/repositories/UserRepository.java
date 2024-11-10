package projektek.GameSite.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projektek.GameSite.models.data.user.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.username LIKE %:query%")
    List<User> searchByUsername(@Param("query")String query);
}
