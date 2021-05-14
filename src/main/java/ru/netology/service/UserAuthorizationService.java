package ru.netology.service;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;
import ru.netology.exception.InvalidCredentials;
import ru.netology.exception.UnauthorizedUser;
import ru.netology.model.Authorities;
import ru.netology.repository.UserRepository;

import java.util.List;

@Service
public class UserAuthorizationService {
    private final UserRepository repository;

    public UserAuthorizationService(UserRepository repository) {
        this.repository = repository;
    }

    public List<Authorities> getUserAuthorities(String user, String password) {
        if (isEmpty(user) || isEmpty(password)) {
            throw new InvalidCredentials("User name or password is empty");
        }
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(password.getBytes());
        List<Authorities> userAuthorities = repository.getUserAuthorities(user, Hex.toHexString(digest));
        if (isEmpty(userAuthorities)) {
            throw new UnauthorizedUser("Unknown user " + user);
        }
        return userAuthorities;
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmpty(List<?> str) {
        return str == null || str.isEmpty();
    }
}
