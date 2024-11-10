package projektek.GameSite.models.data.game.fitw;

import java.util.ArrayList;

public class CreateBoardFITW {
    private final FieldFITW[] field;
    private final PieceFITW fly;
    private final PieceFITW[] spider;

    /**
     * Generating game board, making connections inside it and placing pieces onto it.
     */
    public CreateBoardFITW() {
        field = new FieldFITW[28];

        for (int i = 0; i < field.length; i++) {
            field[i] = new FieldFITW(PiecesFITW.EMPTY);
            field[i].setConnections(new ArrayList<>());
            field[i].setNumber(i);
        }

        spider = new PieceFITW[5];
        spider[0] = new PieceFITW(PiecesFITW.SPIDER, 0);
        spider[1] = new PieceFITW(PiecesFITW.SPIDER, 5);
        spider[2] = new PieceFITW(PiecesFITW.SPIDER, 10);
        spider[3] = new PieceFITW(PiecesFITW.SPIDER, 14);
        spider[4] = new PieceFITW(PiecesFITW.SPIDER, 18);
        fly = new PieceFITW(PiecesFITW.FLY, 27);

        field[27].setPiece(PiecesFITW.FLY);
        field[0].setPiece(PiecesFITW.SPIDER);
        field[5].setPiece(PiecesFITW.SPIDER);
        field[10].setPiece(PiecesFITW.SPIDER);
        field[14].setPiece(PiecesFITW.SPIDER);
        field[18].setPiece(PiecesFITW.SPIDER);

        field[0].getConnections().add(field[5]);
        field[0].getConnections().add(field[22]);
        field[0].getConnections().add(field[6]);
        field[0].getConnections().add(field[1]);

        field[1].getConnections().add(field[7]);
        field[1].getConnections().add(field[23]);
        field[1].getConnections().add(field[2]);
        field[1].getConnections().add(field[0]);

        field[2].getConnections().add(field[7]);
        field[2].getConnections().add(field[24]);
        field[2].getConnections().add(field[3]);
        field[2].getConnections().add(field[1]);

        field[3].getConnections().add(field[9]);
        field[3].getConnections().add(field[25]);
        field[3].getConnections().add(field[4]);
        field[3].getConnections().add(field[2]);

        field[4].getConnections().add(field[9]);
        field[4].getConnections().add(field[26]);
        field[4].getConnections().add(field[27]);
        field[4].getConnections().add(field[3]);

        field[5].getConnections().add(field[0]);
        field[5].getConnections().add(field[10]);
        field[5].getConnections().add(field[6]);

        field[6].getConnections().add(field[0]);
        field[6].getConnections().add(field[11]);
        field[6].getConnections().add(field[7]);
        field[6].getConnections().add(field[5]);

        field[7].getConnections().add(field[2]);
        field[7].getConnections().add(field[1]);
        field[7].getConnections().add(field[11]);
        field[7].getConnections().add(field[8]);
        field[7].getConnections().add(field[6]);

        field[8].getConnections().add(field[12]);
        field[8].getConnections().add(field[9]);
        field[8].getConnections().add(field[7]);

        field[9].getConnections().add(field[4]);
        field[9].getConnections().add(field[3]);
        field[9].getConnections().add(field[13]);
        field[9].getConnections().add(field[27]);
        field[9].getConnections().add(field[8]);

        field[10].getConnections().add(field[5]);
        field[10].getConnections().add(field[14]);
        field[10].getConnections().add(field[15]);
        field[10].getConnections().add(field[11]);

        field[11].getConnections().add(field[6]);
        field[11].getConnections().add(field[7]);
        field[11].getConnections().add(field[15]);
        field[11].getConnections().add(field[12]);
        field[11].getConnections().add(field[10]);

        field[12].getConnections().add(field[8]);
        field[12].getConnections().add(field[16]);
        field[12].getConnections().add(field[13]);
        field[12].getConnections().add(field[11]);

        field[13].getConnections().add(field[9]);
        field[13].getConnections().add(field[17]);
        field[13].getConnections().add(field[27]);
        field[13].getConnections().add(field[12]);

        field[14].getConnections().add(field[10]);
        field[14].getConnections().add(field[18]);
        field[14].getConnections().add(field[19]);
        field[14].getConnections().add(field[15]);

        field[15].getConnections().add(field[10]);
        field[15].getConnections().add(field[11]);
        field[15].getConnections().add(field[20]);
        field[15].getConnections().add(field[16]);
        field[15].getConnections().add(field[14]);

        field[16].getConnections().add(field[12]);
        field[16].getConnections().add(field[20]);
        field[16].getConnections().add(field[21]);
        field[16].getConnections().add(field[17]);
        field[16].getConnections().add(field[15]);

        field[17].getConnections().add(field[13]);
        field[17].getConnections().add(field[21]);
        field[17].getConnections().add(field[27]);
        field[17].getConnections().add(field[16]);

        field[18].getConnections().add(field[14]);
        field[18].getConnections().add(field[22]);
        field[18].getConnections().add(field[24]);
        field[18].getConnections().add(field[19]);

        field[19].getConnections().add(field[14]);
        field[19].getConnections().add(field[24]);
        field[19].getConnections().add(field[20]);
        field[19].getConnections().add(field[18]);

        field[20].getConnections().add(field[15]);
        field[20].getConnections().add(field[16]);
        field[20].getConnections().add(field[25]);
        field[20].getConnections().add(field[21]);
        field[20].getConnections().add(field[19]);

        field[21].getConnections().add(field[16]);
        field[21].getConnections().add(field[17]);
        field[21].getConnections().add(field[26]);
        field[21].getConnections().add(field[27]);
        field[21].getConnections().add(field[20]);

        field[22].getConnections().add(field[18]);
        field[22].getConnections().add(field[0]);
        field[22].getConnections().add(field[23]);

        field[23].getConnections().add(field[1]);
        field[23].getConnections().add(field[24]);
        field[23].getConnections().add(field[22]);

        field[24].getConnections().add(field[18]);
        field[24].getConnections().add(field[19]);
        field[24].getConnections().add(field[2]);
        field[24].getConnections().add(field[25]);
        field[24].getConnections().add(field[23]);

        field[25].getConnections().add(field[20]);
        field[25].getConnections().add(field[3]);
        field[25].getConnections().add(field[26]);
        field[25].getConnections().add(field[24]);

        field[26].getConnections().add(field[21]);
        field[26].getConnections().add(field[4]);
        field[26].getConnections().add(field[27]);
        field[26].getConnections().add(field[25]);

        field[27].getConnections().add(field[4]);
        field[27].getConnections().add(field[9]);
        field[27].getConnections().add(field[13]);
        field[27].getConnections().add(field[17]);
        field[27].getConnections().add(field[21]);
        field[27].getConnections().add(field[26]);
    }

    public FieldFITW[] giveField() {
        return field;
    }

    protected PieceFITW giveFly() {
        return fly;
    }

    protected PieceFITW[] giveSpiders() {
        return spider;
    }
}
