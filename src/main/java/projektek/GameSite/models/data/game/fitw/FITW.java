package projektek.GameSite.models.data.game.fitw;

import java.util.Random;

public class FITW {
    public Long id;
    public final FieldFITW[] field;
    public final PieceFITW fly;
    public final PieceFITW[] spider;


    public short gameMode;
    public final short PVP = 1;
    public final short PVS = 2;
    public final short PVF = 3;
    public final Random random;
    public boolean isGameRunning;
    public boolean isFlysTurn;
    public int pieceWon;
    public int flyStepsDone;
    public int spiderStepsDone;



    public FITW() {
        random = new Random();
        CreateBoardFITW cb = new CreateBoardFITW();
        field = cb.giveField();
        fly = cb.giveFly();
        spider = cb.giveSpiders();
        isGameRunning = true;
        isFlysTurn = true;
        pieceWon = 0;
        flyStepsDone = 0;
        spiderStepsDone = 0;
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

    public short getGameMode() {
        return gameMode;
    }

    public void setGameMode(short gameMode) {
        this.gameMode = gameMode;
    }

    public short getPVP() {
        return PVP;
    }

    public short getPVS() {
        return PVS;
    }

    public short getPVF() {
        return PVF;
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

    public int getFlyStepsDone() {
        return flyStepsDone;
    }

    public void setFlyStepsDone(int flyStepsDone) {
        this.flyStepsDone = flyStepsDone;
    }

    public int getSpiderStepsDone() {
        return spiderStepsDone;
    }

    public void setSpiderStepsDone(int spiderStepsDone) {
        this.spiderStepsDone = spiderStepsDone;
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
}
