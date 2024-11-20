package projektek.GameSite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projektek.GameSite.dtos.LobbyDto;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.services.interfaces.LobbyService;
import projektek.GameSite.services.interfaces.UserService;

@RestController
@RequestMapping("/api/lobby")
public class LobbyController {
    private final LobbyService lobbyService;
    private final UserService userService;

    @Autowired
    public LobbyController(LobbyService lobbyService, UserService userService) {
        this.lobbyService = lobbyService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getLobby() {
        User user = userService.getUserByAuth();
        LobbyDto lobby = lobbyService.getLobby(user);
        return ResponseEntity.ok().body(new ApiResponse(
                "success", "Lobby returned", lobby)
        );
    }

    @PostMapping("/{gameId}/create")
    public ResponseEntity<Object> create(@PathVariable Long gameId) {
        User user = userService.getUserByAuth();
        LobbyDto lobby = lobbyService.getOrCreate(user, gameId);
        return ResponseEntity.ok().body(new ApiResponse(
                "success", "Lobby created", lobby)
        );
    }

    @PostMapping("/{friendUsername}/invite")
    public ResponseEntity<Object> inviteFriend(@PathVariable String friendUsername) {
        User user = userService.getUserByAuth();
        User friend = userService.getUserByUsername(friendUsername);
        lobbyService.inviteFriend(user, friend);
        return ResponseEntity.ok().body(new ApiResponse(
                "success", "Friend invited", null)
        );
    }

    @PostMapping("/{lobbyId}/join")
    public ResponseEntity<Object> joinLobby(@PathVariable Long lobbyId) {
        User user = userService.getUserByAuth();
        LobbyDto lobby = lobbyService.joinLobby(lobbyId, user);
        return ResponseEntity.ok().body(new ApiResponse(
                "success", "Joined to lobby", lobby)
        );
    }

    @PostMapping("/leave")
    public ResponseEntity<Object> leaveLobby() {
        User user = userService.getUserByAuth();
        lobbyService.leaveLobby(user);
        return ResponseEntity.ok().body(new ApiResponse(
                "success", "Left lobby", null)
        );
    }

    @PostMapping("/kick/{username}")
    public ResponseEntity<Object> kickPlayer(@PathVariable String username) {
        User admin = userService.getUserByAuth();
        User user = userService.getUserByUsername(username);
        lobbyService.kickPlayerFromLobby(admin, user);
        return ResponseEntity.ok().body(new ApiResponse(
                "success", "Player kicked from lobby", null)
        );
    }

    @PostMapping("/ready")
    public ResponseEntity<Object> readyUp() {
        User user = userService.getUserByAuth();
        lobbyService.readyUp(user);
        return ResponseEntity.ok().body(new ApiResponse(
                "success", "Ready", null)
        );
    }

    @PostMapping("/unready")
    public ResponseEntity<Object> unready() {
        User user = userService.getUserByAuth();
        lobbyService.unready(user);
        return ResponseEntity.ok().body(new ApiResponse(
                "success", "Unready", null)
        );
    }

    @PostMapping("/start")
    public ResponseEntity<Object> startGame() {
        User user = userService.getUserByAuth();
        lobbyService.startGame(user);
        return ResponseEntity.ok().body(new ApiResponse(
                "success", "Game started", null)
        );
    }
}
