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
import projektek.GameSite.services.interfaces.AuthenticationService;

@RestController
public class AuthenticationController {
    private final AuthenticationService service;

    @Autowired
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register (@RequestBody RegistrationDto dto) {
        AuthenticatedUserDto user = service.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse("success", "You have registered successfully", user)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto dto) {
        AuthenticatedUserDto user = service.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("success", "You have logged in successfully", user)
        );
    }

}
