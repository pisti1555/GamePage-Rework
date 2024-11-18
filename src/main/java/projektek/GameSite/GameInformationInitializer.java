package projektek.GameSite;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import projektek.GameSite.models.data.game.GameInformation;
import projektek.GameSite.models.data.user.Role;
import projektek.GameSite.models.repositories.GameInformationRepository;

@Component
public class GameInformationInitializer {
    private final GameInformationRepository repository;

    @Autowired
    public GameInformationInitializer(GameInformationRepository repository) {
        this.repository = repository;
    }

    @Bean
    CommandLineRunner initializeGameInformation() {
        Dotenv env = Dotenv.load();
        return args -> {
            if (repository.findByName("Fly in the web") == null) {
                repository.save(new GameInformation(
                        "Fly in the web",
                        "/fly-in-the-web",
                        env.get("APP_URL") + "thumbnails/fitw_thumbnail.jpg",
                        env.get("APP_URL") + "images/fitw_background.jpg",
                        "Init"
                ));
            }
            if (repository.findByName("TicTacToe") == null) {
                repository.save(new GameInformation(
                        "TicTacToe",
                        "/tic-tac-toe",
                        env.get("APP_URL") + "thumbnails/tictactoe_thumbnail.jpeg",
                        env.get("APP_URL") + "images/tictactoe_background.jpg",
                        "Init"
                ));
            }
        };
    }
}
