package projektek.GameSite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projektek.GameSite.dtos.GameStatsDto;
import projektek.GameSite.dtos.Mapper;
import projektek.GameSite.dtos.UserDto;
import projektek.GameSite.models.data.user.User;
import projektek.GameSite.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService service;
    private final Mapper mapper;

    @Autowired
    public UserController(UserService service, Mapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser() {
        User user = service.getUserByAuth();
        UserDto dto = mapper.map(user, UserDto.class);

        return ResponseEntity.ok().body(new ApiResponse(
                "success",
                "Authenticated user data",
                dto
        ));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Object> getUser(@PathVariable String username) {
        User user = service.getUserByUsername(username);
        UserDto dto = mapper.map(user, UserDto.class);
        return ResponseEntity.ok().body(new ApiResponse(
                "success",
                "User found by username",
                dto
        ));
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() {
        List<UserDto> users = service.getAllUsers();
        return ResponseEntity.ok().body(new ApiResponse(
                "success",
                "List of users returned",
                users
        ));
    }
}
