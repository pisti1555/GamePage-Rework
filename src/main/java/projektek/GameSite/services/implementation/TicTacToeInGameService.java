package projektek.GameSite.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import projektek.GameSite.dtos.GameStatusDto;
import projektek.GameSite.dtos.UserDto;
import projektek.GameSite.exceptions.NotFoundException;
import projektek.GameSite.models.data.lobbies.Lobby;
import projektek.GameSite.models.data.stats.TicTacToeStats;
import projektek.GameSite.models.data.game.tictactoe.PiecesTicTacToe;
import projektek.GameSite.models.data.game.tictactoe.TicTacToe;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.lobby.LobbyRepository;
import projektek.GameSite.models.repositories.user.UserRepository;
import projektek.GameSite.services.interfaces.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TicTacToeInGameService {
    private final LobbyRepository lobbyRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final SimpMessagingTemplate template;

    @Autowired
    public TicTacToeInGameService(LobbyRepository lobbyRepository, UserRepository userRepository, UserService userService, SimpMessagingTemplate template) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.template = template;
    }

    public void move(int row, int col) {
        Lobby lobby = getLobbyByAuth();
        TicTacToe game = getBoardByAuth();
        PiecesTicTacToe piece = lobby.getMembers().get(0).equals(userService.getUserByAuth())
                ? PiecesTicTacToe.X : PiecesTicTacToe.O;

        if (!isMoveValid(row, col, piece, game)) return;

        game.getBoard()[row][col] = piece;
        game.setXTurn(!game.isXTurn());

        if (piece.equals(PiecesTicTacToe.X)) {
            game.setX_movesMade(game.getX_movesMade() + 1);
            update();
        } else {
            game.setO_movesMade(game.getO_movesMade() + 1);
            update();
        }

        if (isWon(game)) {
            gameOver();
        }
    }

    private boolean isMoveValid(int row, int col, PiecesTicTacToe piece, TicTacToe game) {
        if (isWon(game)) return false;

        if (game.getBoard()[row][col] == PiecesTicTacToe.EMPTY) {
            if (piece.equals(PiecesTicTacToe.X) && game.isXTurn()) {
                return true;
            }
            if (piece.equals(PiecesTicTacToe.O) && !game.isXTurn()) {
                return true;
            }
        }
        return false;
    }

    public boolean isWon(TicTacToe game) {
        PiecesTicTacToe[][] board = game.getBoard();
        PiecesTicTacToe X = PiecesTicTacToe.X;
        PiecesTicTacToe O = PiecesTicTacToe.O;
        if (
                board[0][0].equals(X) && board[0][1].equals(X) && board[0][2].equals(X) ||
                        board[1][0].equals(X) && board[1][1].equals(X) && board[1][2].equals(X) ||
                        board[2][0].equals(X) && board[2][1].equals(X) && board[2][2].equals(X) ||
                        board[0][0].equals(X) && board[1][0].equals(X) && board[2][0].equals(X) ||
                        board[0][1].equals(X) && board[1][1].equals(X) && board[2][1].equals(X) ||
                        board[0][2].equals(X) && board[1][2].equals(X) && board[2][2].equals(X) ||
                        board[0][0].equals(X) && board[1][1].equals(X) && board[2][2].equals(X) ||
                        board[0][2].equals(X) && board[1][1].equals(X) && board[2][0].equals(X)
        ) {
            game.setGameRunning(false);
            game.setWinnerPiece(X);
            return true;
        }
        if (
                board[0][0].equals(O) && board[0][1].equals(O) && board[0][2].equals(O) ||
                        board[1][0].equals(O) && board[1][1].equals(O) && board[1][2].equals(O) ||
                        board[2][0].equals(O) && board[2][1].equals(O) && board[2][2].equals(O) ||
                        board[0][0].equals(O) && board[1][0].equals(O) && board[2][0].equals(O) ||
                        board[0][1].equals(O) && board[1][1].equals(O) && board[2][1].equals(O) ||
                        board[0][2].equals(O) && board[1][2].equals(O) && board[2][2].equals(O) ||
                        board[0][0].equals(O) && board[1][1].equals(O) && board[2][2].equals(O) ||
                        board[0][2].equals(O) && board[1][1].equals(O) && board[2][0].equals(O)
        ) {
            game.setGameRunning(false);
            game.setWinnerPiece(O);
            return true;
        }

        return false;
    }

    private List<int[]> getFreeFields(TicTacToe game) {
        List<int[]> fields = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.getBoard()[i][j].equals(PiecesTicTacToe.EMPTY)) {
                    fields.add(new int[] {i, j});
                }
            }
        }
        return fields;
    }

    public int[][] getPositions() {
        TicTacToe game = getBoardByAuth();
        int[][] pos = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.getBoard()[i][j] == PiecesTicTacToe.EMPTY) {
                    pos[i][j] = 0;
                }
                if (game.getBoard()[i][j] == PiecesTicTacToe.X) {
                    pos[i][j] = 1;
                }
                if (game.getBoard()[i][j] == PiecesTicTacToe.O) {
                    pos[i][j] = 2;
                }
            }
        }
        return pos;
    }

    public short whichWon() {
        TicTacToe game = getBoardByAuth();
        boolean isThereFreeSpace = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.getBoard()[i][j].equals(PiecesTicTacToe.EMPTY)) {
                    isThereFreeSpace = true;
                }
            }
        }

        if (!isThereFreeSpace && game.getWinnerPiece().equals(PiecesTicTacToe.EMPTY)) return 3;
        if (game.getWinnerPiece().equals(PiecesTicTacToe.X)) return 1;
        if (game.getWinnerPiece().equals(PiecesTicTacToe.O)) return 2;
        return 0;
    }

    public int gameOver() {
        Lobby lobby = getLobbyByAuth();
        if (((TicTacToe) lobby.getBoard()).isSaved()) return whichWon();

        User user1 = lobby.getMembers().get(0);
        User user2 = lobby.getMembers().get(1);
        TicTacToeStats user1Stats = user1.getTicTacToeStats();
        TicTacToeStats user2Stats = user2.getTicTacToeStats();

        if (whichWon() == 1) {
            user1Stats.setMoves(user1Stats.getMoves() + ((TicTacToe) lobby.getBoard()).getX_movesMade());
            user1Stats.setPlayed(user1Stats.getPlayed() + 1);
            user1Stats.setWon(user1Stats.getWon() + 1);

            user2Stats.setMoves(user2Stats.getMoves() + ((TicTacToe) lobby.getBoard()).getO_movesMade());
            user2Stats.setPlayed(user2Stats.getPlayed() + 1);
        } else if (whichWon() == 2) {
            user2Stats.setMoves(user2Stats.getMoves() + ((TicTacToe) lobby.getBoard()).getO_movesMade());
            user2Stats.setPlayed(user2Stats.getPlayed() + 1);
            user2Stats.setWon(user2Stats.getWon() + 1);

            user1Stats.setMoves(user1Stats.getMoves() + ((TicTacToe) lobby.getBoard()).getX_movesMade());
            user1Stats.setPlayed(user1Stats.getPlayed() + 1);
        }

        userRepository.save(user1);
        userRepository.save(user2);

        getBoardByAuth().setSaved(true);
        return whichWon();
    }

    public void leaveGame() {
        User user = userService.getUserByAuth();
        Lobby lobby = getLobbyByAuth();
        updateStatsForUser(user, lobby, false);
        lobbyRepository.removeUserFromLobby(lobby.getId(), user);

        if (lobby.getMembers().size() == 1) {
            Optional<User> winner = lobby.getMembers().stream().findFirst();
            if (winner.isEmpty()) return;
            updateStatsForUser(winner.get(), lobby, true);
            lobbyRepository.removeUserFromLobby(lobby.getId(), winner.get());
        }

        update();
    }

    private Lobby getLobbyByAuth() {
        return lobbyRepository.getLobbyByAnyUser(userService.getUserByAuth()).orElseThrow(
                () -> new NotFoundException(
                        "Lobby was not found",
                        Map.of("lobby", "You are not in a lobby")
                )
        );
    }
    private TicTacToe getBoardByAuth() {
        Lobby lobby = getLobbyByAuth();
        return (TicTacToe) lobby.getBoard();
    }

    public void update() {
        Lobby lobby = getLobbyByAuth();
        template.convertAndSend("/topic/game/tic-tac-toe", "update");

        for (User u : lobby.getMembers()) {
            Optional<User> user = userRepository.findById(u.getId());
            if (user.isPresent()) {
                template.convertAndSendToUser(user.get().getUsername(), "/topic/game/tic-tac-toe", "update");
            }
        }
    }

    public GameStatusDto getGameStatus() {
        TicTacToe game = getBoardByAuth();
        Lobby lobby = getLobbyByAuth();

        List<UserDto> inGameMembers = new ArrayList<>();
        List<Integer> movesOfMembers = new ArrayList<>();
        int winnerPiece = whichWon();

        lobby.getInGameMembers().forEach(member -> inGameMembers.add(
                new UserDto(member)
        ));

        movesOfMembers.add(0, game.getX_movesMade());
        movesOfMembers.add(1, game.getO_movesMade());

        return new GameStatusDto(
                inGameMembers,
                winnerPiece,
                movesOfMembers,
                this.getPositions()
        );
    }

    private void updateStatsForUser(User user, Lobby lobby, boolean isWinner) {
        TicTacToe game = (TicTacToe) lobby.getBoard();
        if (game.isSaved()) return;

        boolean isMainPiece = false;
        if (lobby.getMembers().get(0).equals(user)) isMainPiece = true;
        TicTacToeStats stats = user.getTicTacToeStats();

        if (isMainPiece) {
            stats.setMoves(stats.getMoves() + game.getX_movesMade());
        } else {
            stats.setMoves(stats.getMoves() + game.getO_movesMade());
        }
        stats.setPlayed(stats.getPlayed() + 1);
        if (isWinner) {
            stats.setWon(stats.getWon() + 1);
            game.setSaved(true);
        }

        user.setTicTacToeStats(stats);
        userRepository.save(user);
    }
}
