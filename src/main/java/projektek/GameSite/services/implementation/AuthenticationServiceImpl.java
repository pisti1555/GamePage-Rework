package projektek.GameSite.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projektek.GameSite.dtos.auth.AuthenticatedUserDto;
import projektek.GameSite.dtos.auth.LoginDto;
import projektek.GameSite.dtos.auth.RegistrationDto;
import projektek.GameSite.exceptions.BadRequestException;
import projektek.GameSite.exceptions.UnauthorizedException;
import projektek.GameSite.exceptions.UnexpectedException;
import projektek.GameSite.models.data.user.Role;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.models.repositories.RoleRepository;
import projektek.GameSite.models.repositories.UserRepository;
import projektek.GameSite.services.interfaces.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, RoleRepository roleRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = new BCryptPasswordEncoder();
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticatedUserDto register (RegistrationDto dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto.getFirstName().length() < 2) {
            errors.put("firstName", "First name is too short");
        }
        if (dto.getLastName().length() < 2) {
            errors.put("lastName", "Last name is too short");
        }
        String emailRegex = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!dto.getEmail().matches(emailRegex)) {
            errors.put("email", "Invalid email format");
        }
        if (userRepository.findByEmail(dto.getEmail()) != null) {
            errors.put("email", "This e-mail is already in use");
        }
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            errors.put("username", "This username is already in use");
        }
        String passwordRegex = "^(?=.*\\d).{6,}$";
        if (!dto.getPassword().matches(passwordRegex)) {
            errors.put("password", "Password must be at least 6 characters long and contain at least one digit");
        }
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            errors.put("passwordConfirm", "Passwords don't match");
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Registration failed", errors);
        }

        String password = encoder.encode(dto.getPassword());

        User user = new User(
                dto.getLastName(),
                dto.getFirstName(),
                password,
                dto.getEmail(),
                dto.getUsername()
        );

        user.getRoles().add(getUserRole());
        userRepository.save(user);

        return login(new LoginDto(dto.getUsername(), dto.getPassword()));
    }

    @Override
    public AuthenticatedUserDto login (LoginDto dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );
            String token = jwtService.generateToken(dto.getUsername());
            return new AuthenticatedUserDto(authentication.getName(), token);
        } catch (BadCredentialsException e) {
            User user = userRepository.findByUsername(dto.getUsername());
            Map<String, String> errors = new HashMap<>();
            if (user == null) {
                errors.put("username", "User does not exist");
            } else if (!encoder.matches(dto.getPassword(), user.getPassword())) {
                errors.put("password", "Invalid password");
            }

            throw new UnauthorizedException("Authentication failed", errors);
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Override
    public Role getUserRole() {
        return roleRepository.findByAuthority("ROLE_USER");
    }

}
