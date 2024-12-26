package com.javarush.cmd;

import com.javarush.entity.User;
import com.javarush.service.ImageService;
import com.javarush.service.UserService;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Collection;

@AllArgsConstructor
public class Login implements Command {
    private final UserService userService;

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        Collection<User> users = userService.getAll();
        HttpSession session = req.getSession();
        String login = req.getParameter(Constant.LOGIN);
        String password = req.getParameter(Constant.PASSWORD);

        for (User u : users) {
            if (u.getLogin().equalsIgnoreCase(login) && u.getPassword().equals(password)) {
                session.setAttribute(Constant.USER, u);
                break;
            }
        }
        return Go.HOME;
    }
}
