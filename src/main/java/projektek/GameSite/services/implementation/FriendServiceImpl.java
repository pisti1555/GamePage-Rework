package projektek.GameSite.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projektek.GameSite.exceptions.ForbiddenException;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.data.user.friends.Friend;
import projektek.GameSite.models.repositories.FriendRepository;
import projektek.GameSite.services.interfaces.FriendService;
import projektek.GameSite.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    private final FriendRepository repository;
    private final UserService userService;

    @Autowired
    public FriendServiceImpl(FriendRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public void removeFriend(String username) {
        User auth = userService.getUserByAuth();
        User userToRemove = userService.getUserByUsername(username);

        if (!repository.areFriends(auth, userToRemove)) {
            throw new ForbiddenException("This user is not your friend");
        }

        List<Friend> entities = repository.findByUser1OrUser2(auth, userToRemove);
        entities.forEach(x -> repository.delete(x));
    }

    @Override
    public List<User> getFriends() {
        User user = userService.getUserByAuth();
        return repository.getFriends(user);
    }

}
