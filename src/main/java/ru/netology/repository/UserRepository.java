package ru.netology.repository;

import ru.netology.model.Authorities;

import java.util.List;

public interface UserRepository {
    List<Authorities> getUserAuthorities(String user, String password);

    void save(String name, String password, List<Authorities> authorities);
}
