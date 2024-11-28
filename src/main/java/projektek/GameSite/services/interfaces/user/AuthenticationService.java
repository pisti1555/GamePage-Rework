package projektek.GameSite.services.interfaces.user;

import projektek.GameSite.dtos.auth.AuthenticatedUserDto;
import projektek.GameSite.dtos.auth.LoginDto;
import projektek.GameSite.dtos.auth.RegistrationDto;
import projektek.GameSite.models.data.user.Role;

public interface AuthenticationService {
    AuthenticatedUserDto login(LoginDto dto);
    AuthenticatedUserDto register(RegistrationDto dto);
    Role getUserRole();
}
