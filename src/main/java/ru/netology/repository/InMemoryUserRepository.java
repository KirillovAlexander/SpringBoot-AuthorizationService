package ru.netology.repository;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Repository;
import ru.netology.model.Authorities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepository implements UserRepository{

    private Map<String, Map<String, List<Authorities>>> authorities;

    public InMemoryUserRepository() {
        this.authorities = new ConcurrentHashMap<>();
        setTestData();
    }

    @Override
    public void save(String name, String password, List<Authorities> authoritiesList) {
        Map<String, List<Authorities>> authoritiesMap = new ConcurrentHashMap<>();
        authoritiesMap.put(password, authoritiesList);
        authorities.put(name, authoritiesMap);
    }

    @Override
    public List<Authorities> getUserAuthorities(String user, String password) {
        Map<String, List<Authorities>> authoritiesMap = authorities.get(user);
        if (null == authoritiesMap) return null;
        return authoritiesMap.get(password);
    }

    private void setTestData() {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();

        save("admin",
                Hex.toHexString(digestSHA3.digest("admin".getBytes())),
                getFullAuthorities());

        save("user",
                Hex.toHexString(digestSHA3.digest("123".getBytes())),
                getReadWriteAuthorities());

        save("user1",
                Hex.toHexString(digestSHA3.digest("000".getBytes())),
                getReadAuthorities());
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
