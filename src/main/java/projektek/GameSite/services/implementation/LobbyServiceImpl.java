package projektek.GameSite.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projektek.GameSite.dtos.LobbyDto;
import projektek.GameSite.dtos.LobbyInvitationDto;
import projektek.GameSite.dtos.UserDto;
import projektek.GameSite.exceptions.BadRequestException;
import projektek.GameSite.exceptions.ForbiddenException;
import projektek.GameSite.exceptions.NotFoundException;
import projektek.GameSite.models.data.game.GameInformation;
import projektek.GameSite.models.data.game.fitw.FITW;
import projektek.GameSite.models.data.lobbies.Lobby;
import projektek.GameSite.models.data.lobbies.LobbyInvitation;
import projektek.GameSite.models.data.game.tictactoe.TicTacToe;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.game.GameInformationRepository;
import projektek.GameSite.models.repositories.lobby.LobbyInvitationRepository;
import projektek.GameSite.models.repositories.lobby.LobbyRepository;
import projektek.GameSite.services.interfaces.LobbyService;

import java.util.*;

@Service
public class LobbyServiceImpl implements LobbyService {
    private final LobbyRepository lobbyRepository;
    private final LobbyInvitationRepository lobbyInvitationRepository;
    private final GameInformationRepository gameInformationRepository;

    @Autowired
    public LobbyServiceImpl(
            LobbyRepository lobbyRepository,
            LobbyInvitationRepository lobbyInvitationRepository,
            GameInformationRepository gameInformationRepository
    ) {
        this.lobbyRepository = lobbyRepository;
        this.lobbyInvitationRepository = lobbyInvitationRepository;
        this.gameInformationRepository = gameInformationRepository;
    }

    @Override
    public LobbyDto getLobby(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);

        return new LobbyDto(lobby);
    }

    @Override
    public LobbyDto getOrCreate(User user, Long gameId) {
        GameInformation game = findGameById(gameId);
        try {
            Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
            return new LobbyDto(lobby);
        } catch (NotFoundException e) {
            return new LobbyDto(lobbyRepository.createLobby(user, game));
        }
    }

    @Override
    public void leaveLobby(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        lobbyRepository.removeUserFromLobby(lobby.getId(), user);
    }

    @Override
    public void kickPlayerFromLobby(User admin, User userToKick) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(admin);
        if (!lobby.getMembers().contains(userToKick)) {
            throw new BadRequestException(
                    "User is not in the lobby",
                    Map.of("lobby", "Could not find the user in the given lobby")
            );
        }
        if (!lobby.getMembers().get(0).equals(admin)) {
            throw new ForbiddenException(
                    "No permission",
                    Map.of("lobby", "Kick player needs admin permission")
            );
        }

        lobbyRepository.removeUserFromLobby(lobby.getId(), userToKick);
    }

    @Override
    public LobbyDto joinLobby(Long lobbyId, User user) {
        Lobby lobby = lobbyRepository.getLobbyById(lobbyId);
        if (lobby.getMembers().size() >= lobby.getMaxPlayers()) {
            throw new BadRequestException(
                    "Lobby is full",
                    Map.of("lobby", "Lobby is full")
            );
        }
        List<LobbyInvitation> invitations = lobbyInvitationRepository.getInvitationByLobbyId(lobbyId);
        LobbyInvitation invitation = null;

        for (LobbyInvitation inv : invitations) {
            if (inv.getInvited().equals(user)) {
                invitation = inv;
                break;
            }
        }

        if (invitation == null) {
            throw new BadRequestException(
                    "You have not been invited",
                    Map.of("lobby", "Not invited to this lobby")
            );
        }

        lobbyInvitationRepository.deleteRequest(invitation.getInvitationId());
        lobbyRepository.addUserToLobby(lobbyId, user);
        return new LobbyDto(lobby);
    }

    @Override
    public void readyUp(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        if (!lobby.getReadyMembers().contains(user)) {
            lobby.getReadyMembers().add(user);
        }
    }

    @Override
    public void unready(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        lobby.getReadyMembers().remove(user);
    }

    @Override
    public void inviteFriend(User inviter, User invited) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(inviter);
        lobbyInvitationRepository.save(inviter, invited, lobby.getId());
    }

    @Override
    public List<LobbyInvitationDto> getInvitations(User invited) {
        List<LobbyInvitationDto> invitationDtos = new ArrayList<>();
        for (LobbyInvitation invitation : lobbyInvitationRepository.getInvitations(invited)) {
            invitationDtos.add(new LobbyInvitationDto(
                    invitation.getInvitationId(),
                    invitation.getLobbyId(),
                    new UserDto(invitation.getInviter()),
                    new UserDto(invitation.getInvited())
            ));
        }
        return invitationDtos;
    }

    @Override
    public void declineInvitation(Long id) {
        lobbyInvitationRepository.deleteRequest(id);
    }

    @Override
    public void startGame(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        if (!lobby.getMembers().get(0).equals(user)) {
            throw new ForbiddenException(
                    "No permission",
                    Map.of("lobby", "Start game needs admin permission")
            );
        }
        if (!lobby.getReadyMembers().containsAll(lobby.getMembers())) {
            throw new BadRequestException(
                    "Unready",
                    Map.of("lobby", "Lobby is not ready to start")
            );
        }

        GameInformation gameInfo = findGameById(lobby.getGameId());

        if (gameInfo.getName().equals("Fly in the web")) {
            lobby.setBoard(new FITW());
        }
        if (gameInfo.getName().equals("TicTacToe")) {
            lobby.setBoard(new TicTacToe());
        }

        lobby.getReadyMembers().removeAll(lobby.getMembers());
        lobby.getInGameMembers().addAll(lobby.getMembers());
    }

    private GameInformation findGameById(Long id) {
        return gameInformationRepository.findById(id).orElseThrow(
                () -> new BadRequestException(
                        "Could not find game",
                        Map.of("game", "Game does not exist")
                )
        );
    }
}
