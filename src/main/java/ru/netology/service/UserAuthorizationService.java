package ru.netology.service;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;
import ru.netology.exception.InvalidCredentials;
import ru.netology.exception.UnauthorizedUser;
import ru.netology.model.Authorities;
import ru.netology.model.User;
import ru.netology.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;

@Service
public class UserAuthorizationService {
    private final UserRepository repository;

    public UserAuthorizationService(UserRepository repository) {
        this.repository = repository;
    }

    public List<Authorities> getUserAuthorities(User user) {
        List<Authorities> userAuthorities = repository.getUserAuthorities(user);
        if (isEmpty(userAuthorities)) {
            throw new UnauthorizedUser("Unknown user " + user);
        }
        return userAuthorities;
    }

    private boolean isEmpty(List<?> str) {
        return str == null || str.isEmpty();
    }
}
