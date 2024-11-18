package projektek.GameSite.services.interfaces;

import org.springframework.stereotype.Service;
import projektek.GameSite.dtos.GameStatsDto;

import java.util.List;

public interface GamesService {
    GameStatsDto findStatsById(Long id, String game);
    GameStatsDto findStatsByUsername(String username, String game);
    List<GameStatsDto> getStatsOfAllUsers(String game);
}
