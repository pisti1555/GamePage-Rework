package projektek.GameSite.models.data.game.tictactoe;

import java.util.Objects;

public class TicTacToe {
    private Long id;
    private PiecesTicTacToe[][] board;
    private boolean isXTurn;
    private boolean gameMode; //true = PvP : false = PvC
    private boolean isGameRunning;
    private PiecesTicTacToe winnerPiece;
    private int X_movesMade;
    private int O_movesMade;


    public TicTacToe() {
        this.board = new PiecesTicTacToe[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = PiecesTicTacToe.EMPTY;
            }
        }

        this.isXTurn = true;
        this.isGameRunning = false;
        this.winnerPiece = PiecesTicTacToe.EMPTY;
        this.X_movesMade = 0;
        this.O_movesMade = 0;
    }

    public PiecesTicTacToe[][] cloneBoard() {
        PiecesTicTacToe[][] clonedBoard = new PiecesTicTacToe[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clonedBoard[i][j] = board[i][j];
            }
        }
        return clonedBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicTacToe ticTacToe = (TicTacToe) o;
        return Objects.equals(id, ticTacToe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public PiecesTicTacToe[][] getBoard() {
        return board;
    }

    public void setBoard(PiecesTicTacToe[][] board) {
        this.board = board;
    }

    public boolean isXTurn() {
        return isXTurn;
    }

    public void setXTurn(boolean XTurn) {
        isXTurn = XTurn;
    }

    public boolean isGameMode() {
        return gameMode;
    }

    public void setGameMode(boolean gameMode) {
        this.gameMode = gameMode;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }

    public PiecesTicTacToe getWinnerPiece() {
        return winnerPiece;
    }

    public void setWinnerPiece(PiecesTicTacToe winnerPiece) {
        this.winnerPiece = winnerPiece;
    }

    public int getX_movesMade() {
        return X_movesMade;
    }

    public void setX_movesMade(int x_movesMade) {
        X_movesMade = x_movesMade;
    }

    public int getO_movesMade() {
        return O_movesMade;
    }

    public void setO_movesMade(int o_movesMade) {
        O_movesMade = o_movesMade;
    }
}
