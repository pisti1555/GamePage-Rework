package projektek.GameSite.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.data.user.friends.Friend;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUser1OrUser2(User user1, User user2);

    @Query("SELECT u FROM User u JOIN Friend f ON (f.user1 = u OR f.user2 = u) WHERE (f.user1 = :user OR f.user2 = :user) AND u != :user")
    List<User> getFriends(User user);

    @Query("SELECT COUNT(f) > 0 FROM Friend f WHERE (f.user1 = :user1 AND f.user2 = :user2) OR (f.user1 = :user2 AND f.user2 = :user1)")
    boolean areFriends(@Param("user1") User user1, @Param("user2") User user2);
}
