package projektek.GameSite.services.interfaces;

import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.data.user.friends.FriendRequest;

import java.util.List;

public interface FriendRequestService {
    boolean sendRequest(String username);
    List<User> getPendingRequests();
    List<User> getOutgoingRequests();
    void cancelRequest(String username);
    void rejectFriendRequest(String username);
}
