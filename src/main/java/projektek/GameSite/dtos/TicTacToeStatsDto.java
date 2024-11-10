package projektek.GameSite.dtos;

public class TicTacToeStatsDto {
    private int played;
    private int won;
    private int moves;
    private Long userId;
    private String username;

    public TicTacToeStatsDto() {
    }

    public TicTacToeStatsDto(int played, int won, int moves, Long userId) {
        this.played = played;
        this.won = won;
        this.moves = moves;
        this.userId = userId;
    }

    public TicTacToeStatsDto(int played, int won, int moves, Long userId, String username) {
        this.played = played;
        this.won = won;
        this.moves = moves;
        this.userId = userId;
        this.username = username;
    }

    public TicTacToeStatsDto(String username, int moves, int won, int played) {
        this.username = username;
        this.moves = moves;
        this.won = won;
        this.played = played;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
