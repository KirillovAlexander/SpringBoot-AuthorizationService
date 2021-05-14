package ru.netology.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.netology.config.UserAnnotation;
import ru.netology.exception.InvalidCredentials;
import ru.netology.exception.UnauthorizedUser;
import ru.netology.model.Authorities;
import ru.netology.model.User;
import ru.netology.service.UserAuthorizationService;

import javax.validation.Valid;
import java.util.List;

@RestController("/")
public class AuthorizationController {
    private final UserAuthorizationService authorizationService;

    public AuthorizationController(UserAuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@Valid @UserAnnotation User user) {
        return authorizationService.getUserAuthorities(user);
    }

    @GetMapping("/authorizeTest")
    public List<Authorities> getAuthoritiesTest(@Valid @RequestBody User user) {
        return authorizationService.getUserAuthorities(user);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCredentials.class)
    String invalidCredentials(InvalidCredentials e) {
        return e.getLocalizedMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    String methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return e.getLocalizedMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedUser.class)
    String unauthorizedUser(UnauthorizedUser e) {
        return e.getLocalizedMessage();
    }
}
