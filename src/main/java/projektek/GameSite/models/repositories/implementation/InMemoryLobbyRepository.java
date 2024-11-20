package projektek.GameSite.models.repositories.implementation;

import org.springframework.stereotype.Repository;
import projektek.GameSite.models.data.game.GameInformation;
import projektek.GameSite.models.data.game.lobbies.Lobby;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.LobbyRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryLobbyRepository implements LobbyRepository {
    private static final Map<Long, Lobby> STORAGE = new ConcurrentHashMap<>();
    private static Long ID_INCREMENT = 1L;

    @Override
    public Lobby createLobby(User user, GameInformation game) {
        Long id = ID_INCREMENT;
        Lobby lobby = new Lobby(
                id,
                user,
                game.getId(),
                game.getName(),
                game.getMaxPlayers()
        );
        STORAGE.put(id, lobby);
        ID_INCREMENT += 1L;
        return lobby;
    }

    @Override
    public Lobby getLobbyById(Long id) {
        return STORAGE.get(id);
    }

    @Override
    public Lobby getLobbyByAnyUser(User user) {
        for (Lobby lobby : STORAGE.values()) {
            if (lobby.getMembers().contains(user)) return lobby;
        }
        return null;
    }

    @Override
    public boolean deleteLobby(Long id) {
        return STORAGE.remove(id) != null;
    }

    @Override
    public boolean addUserToLobby(Long id, User user) {
        return STORAGE.get(id).getMembers().add(user);
    }

    @Override
    public boolean removeUserFromLobby(Long id, User user) {
        STORAGE.get(id).getMembers().remove(user);
        if (STORAGE.get(id).getMembers().isEmpty()) {
            STORAGE.remove(id);
        }
        return true;
    }
}
