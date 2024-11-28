package projektek.GameSite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projektek.GameSite.dtos.UserDto;
import projektek.GameSite.services.interfaces.user.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser() {
        return ResponseEntity.ok().body(new CustomResponse(
                "Authenticated user data",
                new UserDto(service.getUserByAuth())
        ));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Object> getUser(@PathVariable String username) {
        return ResponseEntity.ok().body(new CustomResponse(
                "User found by username",
                new UserDto(service.getUserByUsername(username))
        ));
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() {
        return ResponseEntity.ok().body(new CustomResponse(
                "List of users returned",
                service.getAllUsers()
        ));
    }
}
