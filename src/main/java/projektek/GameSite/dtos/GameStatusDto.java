package projektek.GameSite.dtos;

import java.util.List;

public class GameStatusDto {
    private final List<UserDto> inGameMembers;
    private final int winnerPiece;
    private final List<Integer> movesOfMembers;

    public GameStatusDto(List<UserDto> inGameMembers, int winnerPiece, List<Integer> movesOfMembers) {
        this.inGameMembers = inGameMembers;
        this.winnerPiece = winnerPiece;
        this.movesOfMembers = movesOfMembers;
    }

    public List<UserDto> getInGameMembers() {
        return inGameMembers;
    }

    public int getWinnerPiece() {
        return winnerPiece;
    }

    public List<Integer> getMovesOfMembers() {
        return movesOfMembers;
    }
}
