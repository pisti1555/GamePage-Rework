package projektek.GameSite.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projektek.GameSite.dtos.GameStatusDto;
import projektek.GameSite.dtos.LobbyDto;
import projektek.GameSite.dtos.UserDto;
import projektek.GameSite.models.data.game.fitw.FITW;
import projektek.GameSite.models.data.game.fitw.PieceFITW;
import projektek.GameSite.models.data.game.fitw.PiecesFITW;
import projektek.GameSite.models.data.lobbies.Lobby;
import projektek.GameSite.models.data.stats.FitwStats;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.lobby.LobbyRepository;
import projektek.GameSite.models.repositories.user.UserRepository;
import projektek.GameSite.services.interfaces.user.UserService;

import java.util.*;

@Service
public class FitwInGameService {
    private final LobbyRepository lobbyRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public FitwInGameService(
            LobbyRepository lobbyRepository,
            UserRepository userRepository,
            UserService userService
    ) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public int move(int from, int to) {
        User user = userService.getUserByAuth();
        FITW board = getBoardByAuth();
        Lobby lobby = getLobbyByAuth();

        if (whichPiece(from) == board.getFly()) {
            if (lobby.getMembers().get(0).equals(user)) {
                if (isMoveValid(from, to)) {
                    board.getField()[to].setPiece(board.getField()[from].getPiece());
                    board.getField()[from].setPiece(PiecesFITW.EMPTY);
                    board.getFly().setLocation(to);
                    board.setFlysTurn(false);
                    board.setFlyMoves(board.getFlyMoves() + 1);
                }
            }
        }
        for (PieceFITW p : board.getSpider()) {
            if (whichPiece(from) == p) {
                if (lobby.getMembers().get(1).equals(user)) {
                    if (isMoveValid(from, to)) {
                        board.getField()[to].setPiece(board.getField()[from].getPiece());
                        board.getField()[from].setPiece(PiecesFITW.EMPTY);
                        p.setLocation(to);
                        board.setFlysTurn(true);
                        board.setSpiderMoves(board.getSpiderMoves() + 1);
                    }
                }
            }
        }

        return checkGameOver();
    }

    public boolean isMoveValid(int from, int to) {
        boolean correctPiece = false;
        FITW board = getBoardByAuth();

        if(board.isFlysTurn()) {
            if (from == board.getFly().getLocation()) {
                correctPiece = true;
            } else return false;
        } else {
            for (int i = 0; i < board.getSpider().length; i++) {
                if (from == board.getSpider()[i].getLocation()) correctPiece = true;
            }
        }

        boolean areFieldsConnected = false;
        boolean isFromFieldEmpty = board.getField()[from].getPiece() == PiecesFITW.EMPTY;
        boolean isToFieldEmpty = board.getField()[to].getPiece() == PiecesFITW.EMPTY;

        for (int i = 0; i < board.getField()[from].getConnections().size(); i++) {
            if (board.getField()[from].getConnections().get(i) == board.getField()[to]) {
                areFieldsConnected = true;
            }
        }

        return checkGameOver() == 0 && !isFromFieldEmpty && isToFieldEmpty && correctPiece && areFieldsConnected;
    }

    public int[] getPositions() {
        FITW board = getBoardByAuth();

        int[] loc = new int[board.getSpider().length + 1];
        loc[0] = board.getFly().getLocation();
        for (int i = 1; i < board.getSpider().length + 1; i++) {
            loc[i] = board.getSpider()[i-1].getLocation();
        }

        return loc;
    }

    public int howManyFieldsAreAvailable() {
        FITW board = getBoardByAuth();

        int availableFields = 0;
        for (int i = 0; i < board.getField()[board.getFly().getLocation()].getConnections().size(); i++) {
            if(
                    board.getField()[board.getFly().getLocation()].getConnections().get(i).getPiece() == PiecesFITW.EMPTY
            ) availableFields++;
        }
        return availableFields;
    }

    public Map<Integer, ArrayList<Integer>> getConnections() {
        FITW board = new FITW();
        HashMap<Integer, ArrayList<Integer>> connections = new HashMap<>();
        for (int i = 0; i < board.getField().length; i++) {
            ArrayList<Integer> connectionOfField = new ArrayList<>();
            for (int j = 0; j < board.getField()[i].getConnections().size(); j++) {
                connectionOfField.add(board.getField()[i].getConnections().get(j).getNumber());
                connections.put(i, connectionOfField);
            }
        }

        return connections;
    }

    public PieceFITW whichPiece(int location) {
        FITW board = getBoardByAuth();

        PieceFITW pieceToReturn = null;
        for (PieceFITW p: board.getSpider()) {
            if (p.getLocation() == location) pieceToReturn = p;
        }
        if (board.getFly().getLocation() == location) pieceToReturn = board.getFly();

        return pieceToReturn;
    }

    public int checkGameOver() {
        FITW board = getBoardByAuth();
        if (
                Set.of(0, 5, 10, 14, 18, 22).contains(board.getFly().getLocation())
        ) {
            board.setGameRunning(false);
            board.setPieceWon(1);
        }

        int availableFields = howManyFieldsAreAvailable();
        if (availableFields == 0) {
            board.setGameRunning(false);
            board.setPieceWon(2);
        }

        if (board.getPieceWon() != 0 && !board.isSaved()) {
            gameOver();
        }
        return board.getPieceWon();
    }

    public int gameOver() {
        Lobby lobby = getLobbyByAuth();
        User user1 = lobby.getMembers().get(0);
        User user2 = lobby.getMembers().get(1);
        FitwStats user1Stats = user1.getFitwStats();
        FitwStats user2Stats = user2.getFitwStats();

        if (checkGameOver() == 1) {
            user1Stats.setMoves(user1Stats.getMoves() + ((FITW) lobby.getBoard()).getFlyMoves());
            user1Stats.setPlayed(user1Stats.getPlayed() + 1);
            user1Stats.setWon(user1Stats.getWon() + 1);

            user2Stats.setMoves(user2Stats.getMoves() + ((FITW) lobby.getBoard()).getSpiderMoves());
            user2Stats.setPlayed(user2Stats.getPlayed() + 1);
        } else if (checkGameOver() == 2) {
            user2Stats.setMoves(user2Stats.getMoves() + ((FITW) lobby.getBoard()).getSpiderMoves());
            user2Stats.setPlayed(user2Stats.getPlayed() + 1);
            user2Stats.setWon(user2Stats.getWon() + 1);

            user1Stats.setMoves(user1Stats.getMoves() + ((FITW) lobby.getBoard()).getFlyMoves());
            user1Stats.setPlayed(user1Stats.getPlayed() + 1);
        }

        user1.setFitwStats(user1Stats);
        user2.setFitwStats(user2Stats);

        userRepository.save(user1);
        userRepository.save(user2);

        getBoardByAuth().setSaved(true);
        return checkGameOver();
    }

    public void newGame() {
        FITW board = new FITW();
        board.setGameRunning(true);
        board.setPieceWon(0);
        board.setFlysTurn(true);
        board.setFlyMoves(0);
        board.setSpiderMoves(0);

        Lobby lobby = getLobbyByAuth();
        lobby.setBoard(board);
    }

    public GameStatusDto getGameStatus() {
        FITW board = getBoardByAuth();
        Lobby lobby = getLobbyByAuth();

        List<UserDto> inGameMembers = new ArrayList<>();
        List<Integer> movesOfMembers = new ArrayList<>();
        int winnerPiece = board.getPieceWon();

        lobby.getInGameMembers().forEach(member -> inGameMembers.add(
                new UserDto(member)
        ));

        movesOfMembers.add(0, board.getFlyMoves());
        movesOfMembers.add(1, board.getSpiderMoves());

        return new GameStatusDto(
                inGameMembers,
                winnerPiece,
                movesOfMembers
        );
    }

    private FITW getBoardByAuth() {
        Lobby lobby = getLobbyByAuth();
        return (FITW) lobby.getBoard();
    }

    private Lobby getLobbyByAuth() {
        return lobbyRepository.getLobbyByAnyUser(userService.getUserByAuth());
    }

    public LobbyDto leaveGame() {
        Lobby lobby = getLobbyByAuth();
        lobby.getInGameMembers().remove(userService.getUserByAuth());
        return new LobbyDto(lobby);
    }
}