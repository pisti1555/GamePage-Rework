package projektek.GameSite.models.repositories.lobby;

import projektek.GameSite.models.data.game.GameInformation;
import projektek.GameSite.models.data.lobbies.Lobby;
import projektek.GameSite.models.data.user.User;

public interface LobbyRepository {
    Lobby createLobby(User user, GameInformation game);
    Lobby getLobbyById(Long id);
    Lobby getLobbyByAnyUser(User user);
    boolean deleteLobby(Long id);
    boolean addUserToLobby(Long id, User user);
    boolean removeUserFromLobby(Long id, User user);
}
