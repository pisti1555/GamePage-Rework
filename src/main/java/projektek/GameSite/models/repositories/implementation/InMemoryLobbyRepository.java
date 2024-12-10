package projektek.GameSite.models.repositories.implementation;

import org.springframework.stereotype.Repository;
import projektek.GameSite.models.data.game.GameInformation;
import projektek.GameSite.models.data.game.fitw.FITW;
import projektek.GameSite.models.data.lobbies.Lobby;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.lobby.LobbyRepository;

import java.util.Map;
import java.util.Optional;
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
    public Optional<Lobby> getLobbyById(Long id) {
        Lobby lobby = STORAGE.get(id);
        return Optional.of(lobby);
    }

    @Override
    public Optional<Lobby> getLobbyByAnyUser(User user) {
        Lobby lobby = null;
        for (Lobby l : STORAGE.values()) {
            if (l.getMembers().contains(user)) lobby = l;
        }
        return Optional.ofNullable(lobby);
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
            return true;
        }

        STORAGE.get(id).getReadyMembers().remove(user);
        STORAGE.get(id).getInGameMembers().remove(user);

        if (STORAGE.get(id).getInGameMembers().isEmpty()) {
            STORAGE.get(id).setBoard(new FITW());
        }
        return true;
    }
}
