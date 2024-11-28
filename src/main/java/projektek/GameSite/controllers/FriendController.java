package projektek.GameSite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projektek.GameSite.dtos.UserDto;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.services.interfaces.user.FriendRequestService;
import projektek.GameSite.services.interfaces.user.FriendService;
import projektek.GameSite.services.interfaces.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    private final UserService userService;
    private final FriendService friendService;
    private final FriendRequestService friendRequestService;

    @Autowired
    public FriendController(UserService userService, FriendService friendService, FriendRequestService friendRequestService) {
        this.userService = userService;
        this.friendService = friendService;
        this.friendRequestService = friendRequestService;
    }

    @GetMapping
    public ResponseEntity<Object> getFriends() {
        List<User> friends = friendService.getFriends();
        List<UserDto> users = friends.stream().map(user -> new UserDto(user)).toList();
        return ResponseEntity.ok().body(
                new CustomResponse("Request was successful", users)
        );
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getFriendsOfUser(@PathVariable String username) {
        List<User> friends = friendService.getFriendsOfUser(username);
        List<UserDto> users = friends.stream().map(user -> new UserDto(user)).toList();
        return ResponseEntity.ok().body(
                new CustomResponse("Request was successful", users)
        );
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> removeFriend(@PathVariable String username) {
        friendService.removeFriend(username);
        return ResponseEntity.ok().body(
                new CustomResponse("Friend removed", null)
        );
    }

    @PostMapping("/add/{username}")
    public ResponseEntity<Object> sendRequest(@PathVariable String username) {
        friendRequestService.sendRequest(username);
        return ResponseEntity.ok().body(
                new CustomResponse("Friend added", null)
        );
    }

    @GetMapping("/requests")
    public ResponseEntity<Object> getFriendRequests() {
        List<User> requests = friendRequestService.getPendingRequests();
        List<UserDto> users = requests.stream().map(user -> new UserDto(user)).toList();
        return ResponseEntity.ok().body(
                new CustomResponse("Request was successful", users)
        );
    }

    @GetMapping("/requests-outgoing")
    public ResponseEntity<Object> getFriendRequestsOutgoing() {
        List<User> requests = friendRequestService.getPendingRequests();
        List<UserDto> users = requests.stream().map(user -> new UserDto(user)).toList();
        return ResponseEntity.ok().body(
                new CustomResponse("Request was successful", users)
        );
    }
}
