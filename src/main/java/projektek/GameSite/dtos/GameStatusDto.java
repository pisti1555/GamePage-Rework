package projektek.GameSite.dtos;

import java.util.List;

public class GameStatusDto {
    private final List<UserDto> inGameMembers;
    private final int winnerPiece;
    private final List<Integer> movesOfMembers;
    private final Object gameBoard;

    public GameStatusDto(List<UserDto> inGameMembers, int winnerPiece, List<Integer> movesOfMembers, Object gameBoard) {
        this.inGameMembers = inGameMembers;
        this.winnerPiece = winnerPiece;
        this.movesOfMembers = movesOfMembers;
        this.gameBoard = gameBoard;
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

    public Object getGameBoard() {
        return gameBoard;
    }
}
