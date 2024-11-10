package projektek.GameSite.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projektek.GameSite.dtos.Mapper;
import projektek.GameSite.dtos.UserDto;
import projektek.GameSite.exceptions.NotFoundException;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.UserRepository;
import projektek.GameSite.services.interfaces.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final Mapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User getUserByAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = repository.findByUsername(auth.getName());
        return user;
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) throw new NotFoundException("Could not find user");
        return user.get();
    }

    @Override
    public User getUserByUsername(String username) {
        User user = repository.findByUsername(username);
        if (user == null) throw new NotFoundException("Could not find user by username");
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = repository.findByEmail(email);
        if (user == null) throw new NotFoundException("Could not find user by e-mail");
        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }
}
