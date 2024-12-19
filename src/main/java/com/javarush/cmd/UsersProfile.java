package com.javarush.cmd;

import com.javarush.entity.Role;
import com.javarush.entity.User;
import com.javarush.service.ImageService;
import com.javarush.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Optional;


@SuppressWarnings("unused")
@AllArgsConstructor
public class UsersProfile implements Command {
    private final UserService userService;
    private final ImageService imageService;


    @Override
    public String doGet(HttpServletRequest req) {
        String stringId = req.getParameter("id");
        if (req.getParameter("id") != null) {
            long id = Long.parseLong(req.getParameter("id"));
            Optional<User> optionalUser = userService.get(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                req.setAttribute("user", user);
            }
        }
        return getView();
    }

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        User user = User.builder()
                .login(req.getParameter("login"))
                .role(Role.valueOf(req.getParameter("role")))
                .build();

        return getView() + "?id=" + user.getId();
    }
}
