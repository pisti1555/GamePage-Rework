package projektek.GameSite.dtos;

import java.util.List;

public class LobbyDto {
    private final Long id;
    private final UserDto admin;
    private final List<UserDto> members;
    private final List<UserDto> readyMembers;
    private final String gameName;
    private final Long gameId;
    private final int maxPlayers;
    private final boolean isReady;
    private final boolean isStarted;

    public LobbyDto(Long id, UserDto admin, List<UserDto> members, List<UserDto> readyMembers, String gameName, Long gameId, int maxPlayers, boolean isReady, boolean isStarted) {
        this.id = id;
        this.admin = admin;
        this.members = members;
        this.readyMembers = readyMembers;
        this.gameName = gameName;
        this.gameId = gameId;
        this.maxPlayers = maxPlayers;
        this.isReady = isReady;
        this.isStarted = isStarted;
    }

    public Long getId() {
        return id;
    }

    public UserDto getAdmin() {
        return admin;
    }

    public List<UserDto> getMembers() {
        return members;
    }

    public List<UserDto> getReadyMembers() {
        return readyMembers;
    }

    public String getGameName() {
        return gameName;
    }

    public Long getGameId() {
        return gameId;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isReady() {
        return isReady;
    }

    public boolean isStarted() {
        return isStarted;
    }
}
