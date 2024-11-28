package projektek.GameSite.models.data.stats;

import jakarta.persistence.*;
import projektek.GameSite.models.data.user.User;

@Entity
@Table(name = "stats_tic_tac_toe")
public class TicTacToeStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String username;
    private int played;
    private int won;
    private int moves;

    public TicTacToeStats(User user) {
        this.won = 0;
        this.moves = 0;
        this.played = 0;
        this.user = user;
        this.username = user.getUsername();
    }

    public TicTacToeStats() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
