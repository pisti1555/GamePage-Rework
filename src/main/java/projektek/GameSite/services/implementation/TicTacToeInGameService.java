package projektek.GameSite.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projektek.GameSite.models.data.lobbies.Lobby;
import projektek.GameSite.models.data.stats.TicTacToeStats;
import projektek.GameSite.models.data.game.tictactoe.PiecesTicTacToe;
import projektek.GameSite.models.data.game.tictactoe.TicTacToe;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.lobby.LobbyRepository;
import projektek.GameSite.models.repositories.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicTacToeInGameService {
    private final LobbyRepository lobbyRepository;
    private final UserRepository userRepository;

    @Autowired
    public TicTacToeInGameService(LobbyRepository lobbyRepository, UserRepository userRepository) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
    }

    public boolean move(int row, int col, TicTacToe game, PiecesTicTacToe piece) {
        if (isMoveValid(row, col, piece, game)) {
            game.getBoard()[row][col] = piece;
            game.setXTurn(!game.isXTurn());
            if (piece.equals(PiecesTicTacToe.X)) {
                game.setX_movesMade(game.getX_movesMade() + 1);
            } else {
                game.setO_movesMade(game.getO_movesMade() + 1);
            }
            return isWon(game);
        }
        return false;
    }

    public boolean isMoveValid(int row, int col, PiecesTicTacToe piece, TicTacToe game) {
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

    public boolean isWon(Object gameObj) {
        TicTacToe game = (TicTacToe) gameObj;

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

    public short whichWon(TicTacToe game) {
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

    public int gameOver(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        TicTacToe game = (TicTacToe) lobby.getBoard();
        User user1 = lobby.getMembers().get(0);
        User user2 = lobby.getMembers().get(1);
        TicTacToeStats user1Stats = user1.getTicTacToeStats();
        TicTacToeStats user2Stats = user2.getTicTacToeStats();

        if (whichWon(game) == 1) {
            user1Stats.setMoves(user1Stats.getMoves() + ((TicTacToe) lobby.getBoard()).getX_movesMade());
            user1Stats.setPlayed(user1Stats.getPlayed() + 1);
            user1Stats.setWon(user1Stats.getWon() + 1);

            user2Stats.setMoves(user2Stats.getMoves() + ((TicTacToe) lobby.getBoard()).getO_movesMade());
            user2Stats.setPlayed(user2Stats.getPlayed() + 1);
        } else if (whichWon(game) == 2) {
            user2Stats.setMoves(user2Stats.getMoves() + ((TicTacToe) lobby.getBoard()).getO_movesMade());
            user2Stats.setPlayed(user2Stats.getPlayed() + 1);
            user2Stats.setWon(user2Stats.getWon() + 1);

            user1Stats.setMoves(user1Stats.getMoves() + ((TicTacToe) lobby.getBoard()).getX_movesMade());
            user1Stats.setPlayed(user1Stats.getPlayed() + 1);
        }

        userRepository.save(user1);
        userRepository.save(user2);
        return whichWon(game);
    }
}
