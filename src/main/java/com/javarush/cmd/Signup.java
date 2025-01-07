package com.javarush.cmd;

import com.javarush.entity.Role;
import com.javarush.entity.User;
import com.javarush.service.ImageService;
import com.javarush.service.UserService;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
@AllArgsConstructor
public class Signup implements Command {
    private final UserService userService;
    private final ImageService imageService;

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        User user;
        String login = req.getParameter(Constant.LOGIN);
        String password = req.getParameter(Constant.PASSWORD);
        if (login.isEmpty() || password.isEmpty()) {
            req.setAttribute("errorMessage", "Все поля должны быть заполнены");
            return getView();
        }
        else {
            user = User.builder()
                    .login(login)
                    .password(password)
                    .role(Role.USER)
                    .build();
            userService.create(user);
            imageService.uploadImage(req, user.getImage());
            HttpSession session = req.getSession();

            session.setAttribute(Constant.USER, user);
        }

        return Go.PROFILE + "?id=" + user.getId();
    }
}
