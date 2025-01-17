package com.javarush.service;

import com.javarush.entity.User;
import com.javarush.exception.AppException;
import com.javarush.repository.UserRepository;
import java.util.Collection;


public class ValidateService {
    private final UserRepository userRepository;

    public ValidateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User validate(String login, String password) {
        Collection<User> users = userRepository.getAll();
        User foundUser = null;
        boolean loginFound = false;

        for (User u : users) {
            if (u.getLogin() != null && u.getLogin().equalsIgnoreCase(login)) {
                loginFound = true;
                if (u.getPassword() != null && u.getPassword().equals(password)) {
                    foundUser = u;
                    break;
                } else {
                    throw new AppException("Wrong password");
                }
            }
        }
        if (!loginFound) {
            throw new AppException("User with login " + login + " not found");
        }
        return foundUser;
    }
}
