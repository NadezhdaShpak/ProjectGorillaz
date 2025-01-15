package com.javarush.service;

import com.javarush.cmd.Signup;
import com.javarush.exception.AppException;
import com.javarush.repository.UserRepository;
import com.javarush.entity.User;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

public class UserService {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user) {
        User login = User.builder().login(user.getLogin()).build();
        if (userRepository.find(login).findAny().isEmpty()) {
            user.setNumberOfWinGames(0L);
            user.setNumberOfLooseGames(0L);
            userRepository.create(user);
        } else {
            throw new AppException("User with login " + user.getLogin() + " already exists");
        }
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    public Optional<User> get(long id) {
        return Optional.ofNullable(userRepository.get(id));
    }
}
