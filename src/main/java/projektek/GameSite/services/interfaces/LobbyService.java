package projektek.GameSite.services.interfaces;

import projektek.GameSite.dtos.LobbyDto;
import projektek.GameSite.dtos.LobbyInvitationDto;
import projektek.GameSite.models.data.lobbies.Lobby;
import projektek.GameSite.models.data.user.User;

import java.util.List;

public interface LobbyService {
    LobbyDto getLobby(User user);
    LobbyDto getOrCreate(User user, Long gameId);
    void leaveLobby(User user);
    void kickPlayerFromLobby(User admin, User userToKick);
    LobbyDto joinLobby(Long lobbyId, User user);
    void readyUp(User user);
    void unready(User user);
    void inviteFriend(User inviter, User invited);
    List<LobbyInvitationDto> getInvitations(User invited);
    void declineInvitation(Long id);
    void startGame(User user);
    void update(Lobby lobby);
}
