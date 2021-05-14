package ru.netology.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.netology.exception.InvalidCredentials;
import ru.netology.exception.UnauthorizedUser;
import ru.netology.model.Authorities;
import ru.netology.service.UserAuthorizationService;

import java.util.List;

@RestController("/")
public class AuthorizationController {
    private final UserAuthorizationService authorizationService;

    public AuthorizationController(UserAuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@RequestParam("user") String user, @RequestParam("password") String password) {
        return authorizationService.getUserAuthorities(user, password);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCredentials.class)
    String invalidCredentials(InvalidCredentials e) {
        return e.getLocalizedMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedUser.class)
    String unauthorizedUser(UnauthorizedUser e) {
        return e.getLocalizedMessage();
    }
}
