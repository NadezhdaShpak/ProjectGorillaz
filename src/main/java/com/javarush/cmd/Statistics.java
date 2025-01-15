package com.javarush.cmd;

import com.javarush.entity.User;
import com.javarush.service.UserService;
import com.javarush.util.Constant;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;

public class Statistics implements Command {
    private final UserService userService;

    public Statistics(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        Collection<User> users = userService.getAll();
        request.setAttribute(Constant.USERS, users);
        return getView();
    }
}
