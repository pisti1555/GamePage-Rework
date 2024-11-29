package projektek.GameSite.models.data.game.fitw;

public class PieceFITW {
    private PiecesFITW type;
    private int location;

    public PieceFITW(PiecesFITW type, int location) {
        this.type = type;
        this.location = location;
    }

    public PiecesFITW getType() {
        return type;
    }

    public void setType(PiecesFITW type) {
        this.type = type;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
