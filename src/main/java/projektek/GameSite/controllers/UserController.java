package projektek.GameSite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projektek.GameSite.dtos.UserDto;
import projektek.GameSite.services.interfaces.UserService;

import java.util.List;

@RestController
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return service.getAllUsers();
    }
}
