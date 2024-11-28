package projektek.GameSite.models.data.lobbies;

import net.minidev.json.annotate.JsonIgnore;
import projektek.GameSite.models.data.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lobby {
    Long id;
    private Long gameId;
    private String gameName;
    private final List<User> members;
    private int maxPlayers;
    private final List<User> readyMembers;
    private final List<User> inGameMembers;
    @JsonIgnore
    private Object board;

    public Lobby(Long id, User user, Long gameId, String gameName, int maxPlayers) {
        this.id = id;
        this.members = new ArrayList<>();
        this.members.add(user);
        this.gameId = gameId;
        this.gameName = gameName;
        this.maxPlayers = maxPlayers;
        this.readyMembers = new ArrayList<>();
        this.inGameMembers = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lobby lobby = (Lobby) o;
        return Objects.equals(id, lobby.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<User> getMembers() {
        return members;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<User> getReadyMembers() {
        return readyMembers;
    }

    public Object getBoard() {
        return board;
    }

    public void setBoard(Object board) {
        this.board = board;
    }

    public List<User> getInGameMembers() {
        return inGameMembers;
    }
}
