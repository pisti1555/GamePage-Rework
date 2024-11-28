package projektek.GameSite.services.interfaces;

import projektek.GameSite.dtos.GameStatsDto;
import projektek.GameSite.models.data.user.User;

import java.util.List;

public interface GamesService {
    GameStatsDto findStatsById(Long id, String game);
    GameStatsDto findStatsByUsername(String username, String game);
    List<GameStatsDto> getStatsOfAllUsers(String game);
}
