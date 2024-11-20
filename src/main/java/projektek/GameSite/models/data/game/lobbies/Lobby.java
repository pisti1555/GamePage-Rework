package projektek.GameSite.models.data.game.lobbies;

import projektek.GameSite.models.data.game.tictactoe.TicTacToe;
import projektek.GameSite.models.data.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lobby {
    Long id;
    private Long gameId;
    private String gameName;
    private User admin;
    private List<User> members;
    private int maxPlayers;
    private List<User> readyMembers;
    private boolean isReady;
    private boolean isStarted;

    public Lobby(Long id, User user, Long gameId, String gameName, int maxPlayers) {
        this.id = id;
        this.members = new ArrayList<>();
        this.admin = user;
        this.members.add(user);
        this.gameId = gameId;
        this.gameName = gameName;
        this.maxPlayers = maxPlayers;
        this.readyMembers = new ArrayList<>();
        this.isReady = false;
        this.isStarted = false;
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

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
