package projektek.GameSite.models.repositories.implementation;

import org.springframework.stereotype.Repository;
import projektek.GameSite.models.data.game.lobbies.LobbyInvitation;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.LobbyInvitationRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryLobbyInvitationRepository implements LobbyInvitationRepository {
    private static final Map<Long, LobbyInvitation> STORAGE = new ConcurrentHashMap<>();
    private static Long ID_INCREMENT = 1L;

    @Override
    public void save(User inviter, User invited, Long lobbyId) {
        Long id = ID_INCREMENT;
        LobbyInvitation invitation = new LobbyInvitation(
                id,
                inviter,
                invited,
                lobbyId
        );
        STORAGE.put(id, invitation);
        ID_INCREMENT += 1L;
    }

    @Override
    public void deleteRequest(Long id) {
        STORAGE.remove(id);
    }

    @Override
    public LobbyInvitation getInvitationByUsers(User inviter, User invited) {
        for (LobbyInvitation invitation : STORAGE.values()) {
            if (invitation.getInviter().equals(inviter) && invitation.getInvited().equals(invited)) {
                return invitation;
            }
        }
        return null;
    }

    @Override
    public LobbyInvitation getInvitationByLobbyId(Long id) {
        for (LobbyInvitation invitation : STORAGE.values()) {
            if (invitation.getLobbyId().equals(id)) return invitation;
        }
        return null;
    }
}
