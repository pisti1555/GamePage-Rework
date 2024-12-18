package projektek.GameSite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projektek.GameSite.dtos.auth.AuthenticatedUserDto;
import projektek.GameSite.dtos.auth.LoginDto;
import projektek.GameSite.dtos.auth.RegistrationDto;
import projektek.GameSite.services.interfaces.user.AuthenticationService;

@RestController
public class AuthenticationController {
    private final AuthenticationService service;

    @Autowired
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/api/register")
    public ResponseEntity<Object> register (@RequestBody RegistrationDto dto) {
        AuthenticatedUserDto user = service.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CustomResponse("You have registered successfully", user)
        );
    }

    @PostMapping("/api/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto dto) {
        AuthenticatedUserDto user = service.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CustomResponse("You have logged in successfully", user)
        );
    }

}
