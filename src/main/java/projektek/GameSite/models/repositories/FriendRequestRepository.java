package projektek.GameSite.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.data.user.friends.FriendRequest;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiver(User receiver);
    List<FriendRequest> findBySender(User sender);
    FriendRequest findBySenderAndReceiver(User sender, User receiver);

    @Query("SELECT COUNT(fr) > 0 FROM FriendRequest fr WHERE fr.sender = :user1 AND fr.receiver = :user2")
    boolean isFriendRequestSentToUser(@Param("user1") User user1, @Param("user2") User user2);

}
