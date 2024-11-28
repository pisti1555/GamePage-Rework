package projektek.GameSite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projektek.GameSite.dtos.GameStatsDto;
import projektek.GameSite.models.data.game.GameInformation;
import projektek.GameSite.models.repositories.game.GameInformationRepository;
import projektek.GameSite.services.interfaces.GamesService;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GamesController {
    private final GameInformationRepository gameInformationRepository;
    private final GamesService gamesService;

    @Autowired
    public GamesController(GameInformationRepository gameInformationRepository, GamesService gamesService) {
        this.gameInformationRepository = gameInformationRepository;
        this.gamesService = gamesService;
    }

    @GetMapping
    public ResponseEntity<Object> getGames() {
        List<GameInformation> games = gameInformationRepository.findAll();
        return ResponseEntity.ok().body(new CustomResponse("List of all existing games in the API", games));
    }

    @GetMapping("/stats/{game}")
    public ResponseEntity<Object> getGameStatsOfAllUsers(@PathVariable String game) {
        List<GameStatsDto> stats = gamesService.getStatsOfAllUsers(game);
        return ResponseEntity.ok().body(new CustomResponse("Stats of player in all games", stats));
    }

    @GetMapping("/stats/{game}/{username}")
    public ResponseEntity<Object> getGameStatsOfUser(@PathVariable String game, @PathVariable String username) {
        GameStatsDto stat = gamesService.findStatsByUsername(username, game);
        return ResponseEntity.ok().body(new CustomResponse("Stats of player in all games", stat));
    }
}
