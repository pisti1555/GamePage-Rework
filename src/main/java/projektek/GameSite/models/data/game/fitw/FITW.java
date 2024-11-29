package projektek.GameSite.models.data.game.fitw;

import java.util.Objects;
import java.util.Random;

public class FITW {
    private Long id;
    private final FieldFITW[] field;
    private final PieceFITW fly;
    private final PieceFITW[] spider;

    private final Random random;
    private boolean isGameRunning;
    private boolean isFlysTurn;
    private int pieceWon;
    private int flyMoves;
    private int spiderMoves;

    private boolean saved;



    public FITW() {
        this.random = new Random();
        CreateBoardFITW cb = new CreateBoardFITW();
        this.field = cb.giveField();
        this.fly = cb.giveFly();
        this.spider = cb.giveSpiders();
        this.isGameRunning = true;
        this.isFlysTurn = true;
        this.pieceWon = 0;
        this.flyMoves = 0;
        this.spiderMoves = 0;
        this.saved = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FITW fitw = (FITW) o;
        return Objects.equals(id, fitw.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public FieldFITW[] getField() {
        return field;
    }

    public PieceFITW getFly() {
        return fly;
    }

    public PieceFITW[] getSpider() {
        return spider;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Random getRandom() {
        return random;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }

    public boolean isFlysTurn() {
        return isFlysTurn;
    }

    public void setFlysTurn(boolean flysTurn) {
        isFlysTurn = flysTurn;
    }

    public int getPieceWon() {
        return pieceWon;
    }

    public void setPieceWon(int pieceWon) {
        this.pieceWon = pieceWon;
    }

    public int getFlyMoves() {
        return flyMoves;
    }

    public void setFlyMoves(int flyMoves) {
        this.flyMoves = flyMoves;
    }

    public int getSpiderMoves() {
        return spiderMoves;
    }

    public void setSpiderMoves(int spiderMoves) {
        this.spiderMoves = spiderMoves;
    }

    public FieldFITW[] cloneBoard() {
        FieldFITW[] clonedBoard = new CreateBoardFITW().giveField();

        for (int i = 0; i < clonedBoard.length; i++) {
            clonedBoard[i].setPiece(PiecesFITW.EMPTY);
        }

        for (int i = 0; i < clonedBoard.length; i++) {
            if (field[i].getPiece().equals(PiecesFITW.FLY)) {
                clonedBoard[i].setPiece(PiecesFITW.FLY);
            }
            if (field[i].getPiece().equals(PiecesFITW.SPIDER)) {
                clonedBoard[i].setPiece(PiecesFITW.SPIDER);
            }
        }

        return clonedBoard;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
