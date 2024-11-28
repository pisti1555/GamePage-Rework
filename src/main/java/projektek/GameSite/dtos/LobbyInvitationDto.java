package projektek.GameSite.dtos;

public class LobbyInvitationDto {
    private final Long invitationId;
    private final Long lobbyId;
    private final UserDto inviter;
    private final UserDto invited;

    public LobbyInvitationDto(Long invitationId, Long lobbyId, UserDto inviter, UserDto invited) {
        this.invitationId = invitationId;
        this.lobbyId = lobbyId;
        this.inviter = inviter;
        this.invited = invited;
    }

    public Long getInvitationId() {
        return invitationId;
    }

    public Long getLobbyId() {
        return lobbyId;
    }

    public UserDto getInviter() {
        return inviter;
    }

    public UserDto getInvited() {
        return invited;
    }
}
