package projektek.GameSite.models.data.lobbies;

import projektek.GameSite.models.data.user.User;

public class LobbyInvitation {
    private final Long invitationId;
    private final User inviter;
    private final User invited;
    private final Long lobbyId;

    public LobbyInvitation(Long invitationId, User inviter, User invited, Long lobbyId) {
        this.invitationId = invitationId;
        this.inviter = inviter;
        this.invited = invited;
        this.lobbyId = lobbyId;
    }

    public Long getInvitationId() {
        return invitationId;
    }

    public User getInviter() {
        return inviter;
    }

    public User getInvited() {
        return invited;
    }

    public Long getLobbyId() {
        return lobbyId;
    }
}
