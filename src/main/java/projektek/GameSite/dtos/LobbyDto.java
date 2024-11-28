package projektek.GameSite.dtos;

import projektek.GameSite.models.data.lobbies.Lobby;

import java.util.ArrayList;
import java.util.List;

public class LobbyDto {
    private final Long id;
    private final List<UserDto> members;
    private final List<UserDto> readyMembers;
    private final List<UserDto> inGameMembers;
    private final String gameName;
    private final Long gameId;
    private final int maxPlayers;

    public LobbyDto(Lobby lobby) {
        this.id = lobby.getId();
        this.gameName = lobby.getGameName();
        this.gameId = lobby.getGameId();
        this.maxPlayers = lobby.getMaxPlayers();
        this.members = new ArrayList<>();
        this.readyMembers = new ArrayList<>();
        this.inGameMembers = new ArrayList<>();

        lobby.getMembers().forEach(member -> {
            members.add(new UserDto(member));
        });
        lobby.getReadyMembers().forEach(member -> {
            readyMembers.add(new UserDto(member));
        });
        lobby.getInGameMembers().forEach(member -> {
            inGameMembers.add(new UserDto(member));
        });
    }

    public Long getId() {
        return id;
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

    public List<UserDto> getInGameMembers() {
        return inGameMembers;
    }
}
