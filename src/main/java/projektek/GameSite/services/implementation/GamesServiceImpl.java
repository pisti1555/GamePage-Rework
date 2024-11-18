package projektek.GameSite.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projektek.GameSite.dtos.GameStatsDto;
import projektek.GameSite.exceptions.NotFoundException;
import projektek.GameSite.models.data.game.stats.FitwStats;
import projektek.GameSite.models.data.game.stats.TicTacToeStats;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.GameInformationRepository;
import projektek.GameSite.models.repositories.UserRepository;
import projektek.GameSite.services.interfaces.GamesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GamesServiceImpl implements GamesService {
    private final UserRepository userRepository;
    private final GameInformationRepository gameInformationRepository;

    @Autowired
    public GamesServiceImpl(UserRepository userRepository, GameInformationRepository gameInformationRepository) {
        this.userRepository = userRepository;
        this.gameInformationRepository = gameInformationRepository;
    }

    @Override
    public GameStatsDto findStatsById(Long id, String game) {
        Optional<User> userToFind = userRepository.findById(id);
        User user = null;
        if (userToFind.isPresent()) user = userToFind.get();
        if (user == null) throw new NotFoundException();

        switch (game) {
            case "fly-in-the-web": {
                FitwStats fitwStats = user.getFitwStats();
                GameStatsDto dto = new GameStatsDto(
                        fitwStats.getPlayed(),
                        fitwStats.getWon(),
                        fitwStats.getMoves(),
                        gameInformationRepository.findByName("Fly in the web").getId(),
                        "Fly in the web",
                        user.getId(),
                        user.getUsername()
                );
                return dto;
            }
            case "tic-tac-toe" : {
                TicTacToeStats ticTacToeStats = user.getTicTacToeStats();
                GameStatsDto dto = new GameStatsDto(
                        ticTacToeStats.getPlayed(),
                        ticTacToeStats.getWon(),
                        ticTacToeStats.getMoves(),
                        gameInformationRepository.findByName("TicTacToe").getId(),
                        "TicTacToe",
                        user.getId(),
                        user.getUsername()
                );
                return  dto;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public GameStatsDto findStatsByUsername(String username, String game) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFoundException();

        switch (game) {
            case "fly-in-the-web": {
                FitwStats fitwStats = user.getFitwStats();
                GameStatsDto dto = new GameStatsDto(
                        fitwStats.getPlayed(),
                        fitwStats.getWon(),
                        fitwStats.getMoves(),
                        gameInformationRepository.findByName("Fly in the web").getId(),
                        "Fly in the web",
                        user.getId(),
                        user.getUsername()
                );
                return dto;
            }
            case "tic-tac-toe" : {
                TicTacToeStats ticTacToeStats = user.getTicTacToeStats();
                GameStatsDto dto = new GameStatsDto(
                        ticTacToeStats.getPlayed(),
                        ticTacToeStats.getWon(),
                        ticTacToeStats.getMoves(),
                        gameInformationRepository.findByName("TicTacToe").getId(),
                        "TicTacToe",
                        user.getId(),
                        user.getUsername()
                );
                return  dto;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public List<GameStatsDto> getStatsOfAllUsers(String game) {
        List<GameStatsDto> stats = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            GameStatsDto stat = findStatsById(user.getId(), game);
            if (stat != null) stats.add(stat);
        });
        return stats;
    }
}
