package projektek.GameSite.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projektek.GameSite.dtos.LobbyDto;
import projektek.GameSite.dtos.Mapper;
import projektek.GameSite.dtos.UserDto;
import projektek.GameSite.exceptions.BadRequestException;
import projektek.GameSite.exceptions.ForbiddenException;
import projektek.GameSite.exceptions.NotFoundException;
import projektek.GameSite.models.data.game.GameInformation;
import projektek.GameSite.models.data.game.lobbies.Lobby;
import projektek.GameSite.models.data.game.lobbies.LobbyInvitation;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.GameInformationRepository;
import projektek.GameSite.models.repositories.LobbyInvitationRepository;
import projektek.GameSite.models.repositories.LobbyRepository;
import projektek.GameSite.services.interfaces.LobbyService;

import java.util.*;

@Service
public class LobbyServiceImpl implements LobbyService {
    private final LobbyRepository lobbyRepository;
    private final LobbyInvitationRepository lobbyInvitationRepository;
    private final GameInformationRepository gameInformationRepository;
    private final Mapper mapper;

    @Autowired
    public LobbyServiceImpl(
            LobbyRepository lobbyRepository,
            LobbyInvitationRepository lobbyInvitationRepository,
            GameInformationRepository gameInformationRepository,
            Mapper mapper
    ) {
        this.lobbyRepository = lobbyRepository;
        this.lobbyInvitationRepository = lobbyInvitationRepository;
        this.gameInformationRepository = gameInformationRepository;
        this.mapper = mapper;
    }

    @Override
    public LobbyDto getLobby(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        if (lobby != null) {
            return convertLobbyToDto(lobby);
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("lobby", "Lobby not found");
            throw new NotFoundException(errors);
        }
    }

    @Override
    public LobbyDto getOrCreate(User user, Long gameId) {
        GameInformation game = findGameById(gameId);
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        if (lobby != null) {
            return convertLobbyToDto(lobby);
        } else lobby = lobbyRepository.createLobby(user, game);
        return convertLobbyToDto(lobby);
    }

    @Override
    public void leaveLobby(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        if (lobby == null) {
            Map<String, String> errors = new HashMap<>();
            errors.put("lobby", "User is not in any lobby");
            throw new BadRequestException(errors);
        }
        lobbyRepository.removeUserFromLobby(lobby.getId(), user);
    }

    @Override
    public void kickPlayerFromLobby(User admin, User userToKick) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(admin);
        Map<String, String> errors = new HashMap<>();
        if (lobby == null) {
            errors.put("lobby", "Could not find a lobby with the given user");
            throw new BadRequestException(errors);
        }
        if (!lobby.getMembers().contains(userToKick)) {
            errors.put("lobby", "Could not find a lobby with the given user");
            throw new BadRequestException(errors);
        }
        if (!lobby.getAdmin().equals(admin)) {
            errors.put("lobby", "Kick player needs admin permission");
            throw new ForbiddenException(errors);
        }

        lobbyRepository.removeUserFromLobby(lobby.getId(), userToKick);
    }

    @Override
    public LobbyDto joinLobby(Long lobbyId, User user) {
        Lobby lobby = lobbyRepository.getLobbyById(lobbyId);
        Map<String, String> errors = new HashMap<>();
        if (lobby == null) {
            errors.put("lobby", "Could not find a lobby with the given ID");
            throw new BadRequestException(errors);
        }
        if (lobby.getMembers().size() >= lobby.getMaxPlayers()) {
            errors.put("lobby", "Lobby is full");
            throw new BadRequestException(errors);
        }
        LobbyInvitation invitation = lobbyInvitationRepository.getInvitationByLobbyId(lobbyId);
        if (invitation == null) {
            errors.put("lobby", "Could not find invitation by the given ID");
            throw new BadRequestException(errors);
        }
        if (!invitation.getInvited().equals(user)) {
            errors.put("lobby", "Not invited to this lobby");
            throw new BadRequestException(errors);
        }
        lobbyInvitationRepository.deleteRequest(invitation.getInvitationId());
        lobbyRepository.addUserToLobby(lobbyId, user);
        return convertLobbyToDto(lobby);
    }

    @Override
    public void readyUp(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        if (!lobby.getReadyMembers().contains(user)) {
            lobby.getReadyMembers().add(user);
            if (lobby.getReadyMembers().containsAll(lobby.getMembers())) {
                lobby.setReady(true);
            }
        }
    }

    @Override
    public void unready(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        lobby.getReadyMembers().remove(user);
        lobby.setReady(false);
    }

    @Override
    public boolean isAllReady(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        return lobby.isReady();
    }

    @Override
    public void inviteFriend(User inviter, User invited) {
        Long lobbyId = lobbyRepository.getLobbyByAnyUser(inviter).getId();
        if (lobbyId == null) {
            throw new BadRequestException();
        }
        lobbyInvitationRepository.save(inviter, invited, lobbyId);
    }

    @Override
    public void declineInvitation(Long id) {
        lobbyInvitationRepository.deleteRequest(id);
    }

    @Override
    public void startGame(User user) {
        Lobby lobby = lobbyRepository.getLobbyByAnyUser(user);
        Map<String, String> errors = new HashMap<>();
        if (lobby == null) {
            errors.put("lobby", "Could not find a lobby with the given user");
            throw new BadRequestException(errors);
        }
        if (!lobby.getAdmin().equals(user)) {
            errors.put("lobby", "Start game needs admin permission");
            throw new BadRequestException(errors);
        }
        if (!lobby.isReady()) {
            errors.put("lobby", "Lobby is not ready to start");
            throw new BadRequestException(errors);
        }
        lobby.setStarted(true);
    }

    private GameInformation findGameById(Long id) {
        Optional<GameInformation> gameOptional = gameInformationRepository.findById(id);
        if (gameOptional.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            errors.put("game", "Game does not exist");
            throw new BadRequestException(errors);
        }
        return gameOptional.get();
    }

    private LobbyDto convertLobbyToDto(Lobby lobby) {
        List<UserDto> members = new ArrayList<>();
        List<UserDto> readyMembers = new ArrayList<>();

        lobby.getMembers().forEach(member -> {
            members.add(mapper.map(member, UserDto.class));
        });
        lobby.getReadyMembers().forEach(member -> {
            readyMembers.add(mapper.map(member, UserDto.class));
        });

        return new LobbyDto(
                lobby.getId(),
                mapper.map(lobby.getAdmin(), UserDto.class),
                members,
                readyMembers,
                lobby.getGameName(),
                lobby.getGameId(),
                lobby.getMaxPlayers(),
                lobby.isReady(),
                lobby.isStarted()
        );
    }
}
