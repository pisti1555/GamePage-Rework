package projektek.GameSite.models.repositories.implementation;

import org.springframework.stereotype.Repository;
import projektek.GameSite.models.data.lobbies.LobbyInvitation;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.lobby.LobbyInvitationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public Optional<LobbyInvitation> getInvitationByUsers(User inviter, User invited) {
        for (LobbyInvitation invitation : STORAGE.values()) {
            if (invitation.getInviter().equals(inviter) && invitation.getInvited().equals(invited)) {
                return Optional.of(invitation);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<LobbyInvitation> getInvitations(User user) {
        List<LobbyInvitation> list = new ArrayList<>();
        for (LobbyInvitation invitation : STORAGE.values()) {
            if (invitation.getInvited().equals(user)) list.add(invitation);
        }
        return list;
    }

    @Override
    public List<LobbyInvitation> getInvitationByLobbyId(Long id) {
        List<LobbyInvitation> invitationsOfLobby = new ArrayList<>();
        for (LobbyInvitation invitation : STORAGE.values()) {
            if (invitation.getLobbyId().equals(id)) invitationsOfLobby.add(invitation);
        }
        return invitationsOfLobby;
    }
}
