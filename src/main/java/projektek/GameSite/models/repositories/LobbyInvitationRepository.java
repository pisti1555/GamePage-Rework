package projektek.GameSite.models.repositories;

import projektek.GameSite.dtos.LobbyDto;
import projektek.GameSite.models.data.game.lobbies.LobbyInvitation;
import projektek.GameSite.models.data.user.User;

public interface LobbyInvitationRepository {
    void save(User inviter, User invited, Long lobbyId);
    void deleteRequest(Long id);
    LobbyInvitation getInvitationByUsers(User inviter, User invited);
    LobbyInvitation getInvitationByLobbyId(Long id);
}