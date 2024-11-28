package projektek.GameSite.services.implementation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projektek.GameSite.dtos.UserDto;
import projektek.GameSite.exceptions.NotFoundException;
import projektek.GameSite.exceptions.UnauthorizedException;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.user.UserRepository;
import projektek.GameSite.services.interfaces.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getUserByAuth() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return repository.findByUsername(auth.getName()).orElseThrow(
                    () -> new UnauthorizedException(
                            "Please log in",
                            Map.of("user", "You are unauthenticated")
                    )
            );
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("Authorization", "You are unauthenticated");
            throw new UnauthorizedException(errors);
        }
    }

    @Override
    public User getUser(Long id) {
        Map<String, String> errors = new HashMap<>();
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            errors.put("user", "Could not find user");
            throw new NotFoundException(errors);
        }
        return user.get();
    }

    @Override
    public User getUserByUsername(String username) {
        Map<String, String> errors = new HashMap<>();
        return repository.findByUsername(username).orElseThrow(
                () -> new NotFoundException(
                        "User was not found",
                        Map.of("user", "Could not find user by username")
                )
        );
    }

    @Override
    public User getUserByEmail(String email) {
        Map<String, String> errors = new HashMap<>();
        return repository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(
                        "User was not found",
                        Map.of("user", "Could not find user by email")
                )
        );
    }

    @Override
    public List<UserDto> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(user -> new UserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(
                () -> new NotFoundException(
                        "User was not found",
                        Map.of("user", "Could not find user by username")
                )
        );
    }
}
