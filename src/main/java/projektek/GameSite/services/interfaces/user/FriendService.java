package projektek.GameSite.services.interfaces.user;

import projektek.GameSite.models.data.user.User;

import java.util.List;

public interface FriendService {
    void removeFriend(String username);
    List<User> getFriends();
    List<User> getFriendsOfUser(String username);
}
