package projektek.GameSite.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projektek.GameSite.exceptions.BadRequestException;
import projektek.GameSite.exceptions.ForbiddenException;
import projektek.GameSite.exceptions.NotFoundException;
import projektek.GameSite.exceptions.UnexpectedException;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.data.user.friends.Friend;
import projektek.GameSite.models.data.user.friends.FriendRequest;
import projektek.GameSite.models.repositories.FriendRepository;
import projektek.GameSite.models.repositories.FriendRequestRepository;
import projektek.GameSite.services.interfaces.FriendRequestService;
import projektek.GameSite.services.interfaces.UserService;

import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestRepository repository;
    private final FriendRepository friendRepository;
    private final UserService userService;

    @Autowired
    public FriendRequestServiceImpl(FriendRequestRepository repository, FriendRepository friendRepository, UserService userService) {
        this.repository = repository;
        this.friendRepository = friendRepository;
        this.userService = userService;
    }

    @Override
    public boolean sendRequest(String username) {
        User sender = userService.getUserByAuth();

        if (sender.getUsername().equals(username)) {
            throw new ForbiddenException("Cannot add yourself as a friend");
        }

        User receiver = userService.getUserByUsername(username);
        if (repository.isFriendRequestSentToUser(sender, receiver)) throw new BadRequestException("Friend request already sent");

        FriendRequest existingRequest = repository.findBySenderAndReceiver(receiver, sender);
        if (existingRequest != null) {
            return acceptFriendRequest(existingRequest);
        }

        FriendRequest request = repository.save(new FriendRequest(sender, receiver));

        return true;
    }

    @Override
    public List<User> getPendingRequests() {
        User user = userService.getUserByAuth();
        List<FriendRequest> requests = repository.findByReceiver(user);
        return requests.stream().map(request -> request.getSender()).toList();
    }

    @Override
    public List<User> getOutgoingRequests() {
        User user = userService.getUserByAuth();
        List<FriendRequest> outgoingRequests = repository.findBySender(user);
        return outgoingRequests.stream().map(request -> request.getReceiver()).toList();
    }

    @Override
    public void cancelRequest(String username) {
        User user = userService.getUserByAuth();
        User receiver = userService.getUserByUsername(username);
        FriendRequest request = repository.findBySenderAndReceiver(user, receiver);
        if (request == null) throw new NotFoundException("Could not find friend request");
        destroyRequest(request);
    }

    @Override
    public void rejectFriendRequest(String username) {
        User user = userService.getUserByAuth();
        User sender = userService.getUserByUsername(username);
        FriendRequest request = repository.findBySenderAndReceiver(sender, user);
        if (request == null) throw new NotFoundException("Could not find friend request");
        destroyRequest(request);
    }

    private void destroyRequest(FriendRequest request) {
        repository.delete(request);
    }

    private boolean acceptFriendRequest(FriendRequest request) {
        if (friendRepository.areFriends(request.getSender(), request.getReceiver())) {
            throw new BadRequestException("Cannot add user as friend, because it already is");
        }


        if (friendRepository.areFriends(request.getReceiver(), request.getSender())) {
            throw new BadRequestException("Cannot add user as friend, because it already is");
        }

        friendRepository.save(new Friend(request.getSender(), request.getReceiver()));
        friendRepository.save(new Friend(request.getReceiver(), request.getSender()));

        repository.delete(request);
        return true;
    }
}
