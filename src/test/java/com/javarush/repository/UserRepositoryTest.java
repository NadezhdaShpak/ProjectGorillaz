package com.javarush.repository;

import com.javarush.entity.Role;
import com.javarush.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepository();
    private User admin;

    @BeforeEach
    void createTestAdmin() {
        admin = User.builder()
                .id(5L)
                .login("testAdmin")
                .password("testPassword")
                .role(Role.ADMIN)
                .build();
        userRepository.create(admin);
    }

    @Test
    void get() {
        User user = userRepository.get(5L);
        Assertions.assertEquals(admin, user);
    }


    @Test
    void update() {
        admin.setLogin("newLogin");
        userRepository.update(admin);
        assertEquals("newLogin", admin.getLogin());
    }

    @Test
    void delete() {
        long sizeBefore = userRepository.getAll().size();
        userRepository.delete(admin);
        assertTrue(userRepository.getAll().size() == sizeBefore - 1);

    }

    @Test
    void find() {
        User pattern = User.builder().login("testAdmin").build();
        Stream<User> userStream = userRepository.find(pattern);
        Assertions.assertEquals(admin, userStream.findFirst().orElseThrow());
    }


}