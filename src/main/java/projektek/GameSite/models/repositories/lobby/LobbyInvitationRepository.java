package projektek.GameSite.models.repositories.lobby;

import projektek.GameSite.models.data.lobbies.LobbyInvitation;
import projektek.GameSite.models.data.user.User;

import java.util.List;
import java.util.Optional;

public interface LobbyInvitationRepository {
    void save(User inviter, User invited, Long lobbyId);
    void deleteRequest(Long id);
    Optional<LobbyInvitation> getInvitationByUsers(User inviter, User invited);
    List<LobbyInvitation> getInvitations(User user);
    List<LobbyInvitation> getInvitationByLobbyId(Long id);
}