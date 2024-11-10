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
        String password = encoder.encode(dto.getPassword());

        if (userRepository.findByEmail(dto.getEmail()) != null) {
            throw new BadRequestException("This e-mail is already in use");
        }
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new BadRequestException("This username is already in use");
        }

        User user = new User(
                dto.getDescription(),
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
            if (user == null) {
                throw new UnauthorizedException("User does not exist");
            }
            if (!encoder.matches(dto.getPassword(), user.getPassword())) {
                throw new UnauthorizedException("Invalid password");
            }

            throw new UnauthorizedException("Cannot authenticate");
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Override
    public Role getUserRole() {
        return roleRepository.findByAuthority("ROLE_USER");
    }

}
