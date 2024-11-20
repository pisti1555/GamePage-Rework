package projektek.GameSite.services.interfaces;

import projektek.GameSite.dtos.LobbyDto;
import projektek.GameSite.models.data.user.User;

public interface LobbyService {
    LobbyDto getLobby(User user);
    LobbyDto getOrCreate(User user, Long gameId);
    void leaveLobby(User user);
    void kickPlayerFromLobby(User admin, User userToKick);
    LobbyDto joinLobby(Long lobbyId, User user);
    void readyUp(User user);
    void unready(User user);
    boolean isAllReady(User user);
    void inviteFriend(User inviter, User invited);
    void declineInvitation(Long id);
    void startGame(User user);
}
