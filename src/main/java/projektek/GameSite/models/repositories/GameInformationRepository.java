package projektek.GameSite.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projektek.GameSite.models.data.game.GameInformation;

public interface GameInformationRepository extends JpaRepository<GameInformation, Long> {
    GameInformation findByName(String name);
}
