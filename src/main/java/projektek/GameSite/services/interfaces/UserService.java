package projektek.GameSite.services.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import projektek.GameSite.dtos.UserDto;
import projektek.GameSite.models.data.user.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserByAuth();
    User getUser(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    List<UserDto> getAllUsers();
}
