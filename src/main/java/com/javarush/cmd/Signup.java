package com.javarush.cmd;

import com.javarush.entity.Role;
import com.javarush.entity.User;
import com.javarush.service.ImageService;
import com.javarush.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
@AllArgsConstructor
public class Signup implements Command {
    private static final Logger log = LogManager.getLogger(Signup.class);
    private final UserService userService;
    private final ImageService imageService;

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        User user = User.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .role(Role.USER)
                .build();
        userService.create(user);
        imageService.uploadImage(req, user.getImage());
        HttpSession session = req.getSession();

        session.setAttribute("user", user);

        return "/users-profile?id=" + user.getId();
    }
}
