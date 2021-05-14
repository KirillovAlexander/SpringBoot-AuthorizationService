package ru.netology.repository;

import ru.netology.model.Authorities;
import ru.netology.model.User;

import java.util.List;

public interface UserRepository {
    List<Authorities> getUserAuthorities(User user);

    void save(User user, List<Authorities> authorities);
}
