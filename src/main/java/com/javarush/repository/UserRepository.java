package com.javarush.repository;

import com.javarush.entity.Role;
import com.javarush.entity.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class UserRepository extends AbstractRepo<User> {
    public final AtomicLong id = new AtomicLong(5L);


    public UserRepository() {
        map.put(1L, new User(1L, "Alisa", "qwerty", Role.USER));
        map.put(2L, new User(2L, "Bob", "", Role.GUEST));
        map.put(3L, new User(3L, "Carl", "admin", Role.ADMIN));
        map.put(4L, new User(4L, "Khmelov", "admin", Role.ADMIN));
    }

    @Override
    public void create(User entity) {
        entity.setId(id.incrementAndGet());
        update(entity);
    }

    @Override
    public Stream<User> find(User pattern) {
        return map.values()
                .stream()
                .filter(u -> nullOrEquals(pattern.getId(), u.getId()))
                .filter(u -> nullOrEquals(pattern.getLogin(), u.getLogin()))
                .filter(u -> nullOrEquals(pattern.getPassword(), u.getPassword()))
                .filter(u -> nullOrEquals(pattern.getRole(), u.getRole()));
    }
}
