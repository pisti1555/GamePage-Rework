package projektek.GameSite.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projektek.GameSite.exceptions.BadRequestException;
import projektek.GameSite.exceptions.ForbiddenException;
import projektek.GameSite.exceptions.NotFoundException;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.data.user.friends.Friend;
import projektek.GameSite.models.repositories.FriendRepository;
import projektek.GameSite.services.interfaces.FriendService;
import projektek.GameSite.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, String> errors = new HashMap<>();

        if (userToRemove == null) {
            errors.put("user", "Could not find user");
            throw new NotFoundException(errors);
        }

        if (!repository.areFriends(auth, userToRemove)) {
            errors.put("user", "This user is not your friend");
            throw new BadRequestException(errors);
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
