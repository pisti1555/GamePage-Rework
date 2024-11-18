package projektek.GameSite.dtos;

public class GameStatsDto {

    private Long gameId;
    private String gameName;
    private int played;
    private int won;
    private int moves;
    private Long userId;
    private String username;

    public GameStatsDto() {}

    public GameStatsDto(int played, int won, int moves, Long gameId, String gameName, Long userId, String username) {
        this.played = played;
        this.won = won;
        this.moves = moves;
        this.gameId = gameId;
        this.gameName = gameName;
        this.userId = userId;
        this.username = username;
    }

    public int getPlayed() {
        return played;
    }

    public int getWon() {
        return won;
    }

    public int getMoves() {
        return moves;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
