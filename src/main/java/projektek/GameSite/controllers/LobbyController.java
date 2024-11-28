package projektek.GameSite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projektek.GameSite.dtos.LobbyDto;
import projektek.GameSite.dtos.LobbyInvitationDto;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.services.interfaces.LobbyService;
import projektek.GameSite.services.interfaces.user.UserService;

import java.util.List;

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
        return ResponseEntity.ok().body(new CustomResponse(
                "Lobby returned", lobby)
        );
    }

    @PostMapping("/{gameId}/create")
    public ResponseEntity<Object> create(@PathVariable Long gameId) {
        User user = userService.getUserByAuth();
        LobbyDto lobby = lobbyService.getOrCreate(user, gameId);
        return ResponseEntity.ok().body(new CustomResponse(
                "Lobby created", lobby)
        );
    }

    @PostMapping("/{friendUsername}/invite")
    public ResponseEntity<Object> inviteFriend(@PathVariable String friendUsername) {
        User user = userService.getUserByAuth();
        User friend = userService.getUserByUsername(friendUsername);
        lobbyService.inviteFriend(user, friend);
        return ResponseEntity.ok().body(new CustomResponse(
                "Friend invited", null)
        );
    }

    @GetMapping("/invitations")
    public ResponseEntity<Object> getInvitations() {
        User user = userService.getUserByAuth();
        List<LobbyInvitationDto> invitations = lobbyService.getInvitations(user);
        return ResponseEntity.ok().body(new CustomResponse(
                "List of lobby invitations", invitations)
        );
    }

    @PostMapping("/{lobbyId}/join")
    public ResponseEntity<Object> joinLobby(@PathVariable Long lobbyId) {
        User user = userService.getUserByAuth();
        LobbyDto lobby = lobbyService.joinLobby(lobbyId, user);
        return ResponseEntity.ok().body(new CustomResponse(
                "Joined to lobby", lobby)
        );
    }

    @PostMapping("/{invitationId}/decline")
    public ResponseEntity<Object> declineInvitation(@PathVariable Long invitationId) {
        lobbyService.declineInvitation(invitationId);
        return ResponseEntity.ok().body(new CustomResponse(
                "Joined to lobby", lobbyService.getInvitations(userService.getUserByAuth()))
        );
    }

    @PostMapping("/leave")
    public ResponseEntity<Object> leaveLobby() {
        User user = userService.getUserByAuth();
        lobbyService.leaveLobby(user);
        return ResponseEntity.ok().body(new CustomResponse(
                "Left lobby", null)
        );
    }

    @PostMapping("/kick/{username}")
    public ResponseEntity<Object> kickPlayer(@PathVariable String username) {
        User admin = userService.getUserByAuth();
        User user = userService.getUserByUsername(username);
        lobbyService.kickPlayerFromLobby(admin, user);
        return ResponseEntity.ok().body(new CustomResponse(
                "Player kicked from lobby", null)
        );
    }

    @PostMapping("/ready")
    public ResponseEntity<Object> readyUp() {
        User user = userService.getUserByAuth();
        lobbyService.readyUp(user);
        return ResponseEntity.ok().body(new CustomResponse(
                "Ready", null)
        );
    }

    @PostMapping("/unready")
    public ResponseEntity<Object> unready() {
        User user = userService.getUserByAuth();
        lobbyService.unready(user);
        return ResponseEntity.ok().body(new CustomResponse(
                "Unready", null)
        );
    }

    @PostMapping("/start")
    public ResponseEntity<Object> startGame() {
        User user = userService.getUserByAuth();
        lobbyService.startGame(user);
        return ResponseEntity.ok().body(new CustomResponse(
                "Game started", null)
        );
    }
}
