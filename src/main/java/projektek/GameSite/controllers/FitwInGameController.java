package projektek.GameSite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projektek.GameSite.models.repositories.lobby.LobbyRepository;
import projektek.GameSite.services.implementation.FitwInGameService;
import projektek.GameSite.services.interfaces.user.UserService;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/game/fitw")
public class FitwInGameController {
    private final FitwInGameService service;
    private final UserService userService;
    private final LobbyRepository lobbyRepository;

    @Autowired
    public FitwInGameController(FitwInGameService service, UserService userService, LobbyRepository lobbyRepository) {
        this.service = service;
        this.userService = userService;
        this.lobbyRepository = lobbyRepository;
    }

    @PostMapping("/move")
    public int move(@RequestParam("from")int from, @RequestParam("to")int to) {
        service.move(from, to);
        return service.checkGameOver();
    }

    @GetMapping("/get-positions")
    public int[] sendPositionsToClient() {
        return service.getPositions();
    }

    @GetMapping("/get-connections")
    public Map<Integer, ArrayList<Integer>> getConnections() {
        return service.getConnections();
    }

    @PostMapping("/new-game")
    public void newGame() {
        service.newGame();
    }

    @GetMapping("/status")
    public ResponseEntity<CustomResponse> getGameStatus() {
        return ResponseEntity.ok(new CustomResponse(
                "Game status",
                service.getGameStatus()
        ));
    }

    @PostMapping("/leave")
    public ResponseEntity<CustomResponse> leaveGame() {
        return ResponseEntity.ok(new CustomResponse(
                "Game left",
                service.leaveGame()
        ));
    }

}
