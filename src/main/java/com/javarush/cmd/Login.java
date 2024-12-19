package com.javarush.cmd;

import com.javarush.entity.User;
import com.javarush.service.ImageService;
import com.javarush.service.UserService;
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
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        for (User u : users) {
            if (u.getLogin().equalsIgnoreCase(login) && u.getPassword().equals(password)) {
                session.setAttribute("user", u);
                break;
            }
        }
        return "/home";
    }
}
