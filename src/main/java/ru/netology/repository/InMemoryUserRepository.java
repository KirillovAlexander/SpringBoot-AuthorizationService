package ru.netology.repository;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Repository;
import ru.netology.model.Authorities;
import ru.netology.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepository implements UserRepository{

    private Map<User, List<Authorities>> authorities;

    public InMemoryUserRepository() {
        this.authorities = new ConcurrentHashMap<>();
        setTestData();
    }

    @Override
    public void save(User user, List<Authorities> authoritiesList) {
        authorities.put(user, authoritiesList);
    }

    @Override
    public List<Authorities> getUserAuthorities(User user) {
        return authorities.get(user);
    }

    private void setTestData() {
        save(new User("admin", "admin"), getFullAuthorities());
        save(new User("user", "123"), getReadWriteAuthorities());
        save(new User("user1", "000"), getReadAuthorities());
    }

    private static List<Authorities> getFullAuthorities() {
        List<Authorities> readWriteDelete = new ArrayList<>();
        readWriteDelete.add(Authorities.READ);
        readWriteDelete.add(Authorities.DELETE);
        readWriteDelete.add(Authorities.WRITE);
        return readWriteDelete;
    }

    private static List<Authorities> getReadWriteAuthorities() {
        List<Authorities> readWriteDelete = new ArrayList<>();
        readWriteDelete.add(Authorities.READ);
        readWriteDelete.add(Authorities.WRITE);
        return readWriteDelete;
    }

    private static List<Authorities> getReadAuthorities() {
        List<Authorities> readWriteDelete = new ArrayList<>();
        readWriteDelete.add(Authorities.READ);
        return readWriteDelete;
    }

}
