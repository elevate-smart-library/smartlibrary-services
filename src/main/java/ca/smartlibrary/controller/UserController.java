package ca.smartlibrary.controller;

import ca.smartlibrary.dto.User;
import ca.smartlibrary.security.ScopeAuthority;
import ca.smartlibrary.service.AuthenticationService;
import ca.smartlibrary.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Secured({ScopeAuthority.PROFILE_VALUE})
@RequestMapping("/api/v1/users")
@Api(tags = {"User"}, description = "Users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService user) {
        this.userService = user;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/self")
    public User self() {
        return AuthenticationService.getAuthenticatedUser();
    }

}
