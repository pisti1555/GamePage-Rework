package projektek.GameSite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projektek.GameSite.models.repositories.lobby.LobbyRepository;
import projektek.GameSite.services.implementation.TicTacToeInGameService;
import projektek.GameSite.services.interfaces.user.UserService;

@RestController
@RequestMapping("/api/game/tic-tac-toe")
public class TicTacToeInGameController {
    private final TicTacToeInGameService service;
    private final UserService userService;
    private final LobbyRepository lobbyRepository;

    @Autowired
    public TicTacToeInGameController(TicTacToeInGameService service, UserService userService, LobbyRepository lobbyRepository) {
        this.service = service;
        this.userService = userService;
        this.lobbyRepository = lobbyRepository;
    }

    @PostMapping("/move")
    public void move(@RequestParam("row")int row, @RequestParam("col")int col) {
        service.move(row, col);
    }

    @GetMapping("/which-won")
    public short whichWon() {
        return service.whichWon();
    }

    @GetMapping("/get-positions")
    public int[][] getPositions() {
        return service.getPositions();
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
        service.leaveGame();
        return ResponseEntity.ok(new CustomResponse(
                "You have left the game",
                null,
                null
        ));
    }
}
