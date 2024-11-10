package projektek.GameSite.models.data.game.fitw;

import java.util.ArrayList;

public class FieldFITW {
    private ArrayList<FieldFITW> connections;
    private PiecesFITW piece;
    private int number;

    public FieldFITW(PiecesFITW piece) {
        this.piece = piece;
    }

    public PiecesFITW getPiece() {
        return piece;
    }

    public void setPiece(PiecesFITW piece) {
        this.piece = piece;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<FieldFITW> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<FieldFITW> connections) {
        this.connections = connections;
    }
}
