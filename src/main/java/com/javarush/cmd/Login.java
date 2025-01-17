package com.javarush.cmd;

import com.javarush.entity.User;
import com.javarush.exception.AppException;
import com.javarush.service.ImageService;
import com.javarush.service.UserService;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

@AllArgsConstructor
public class Login implements Command {
    private final UserService userService;
    private static final Logger log = LogManager.getLogger(Login.class);


    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        Collection<User> users = userService.getAll();
        HttpSession session = req.getSession();
        String login = req.getParameter(Constant.LOGIN);
        String password = req.getParameter(Constant.PASSWORD);
        boolean userFound = false;
            for (User u : users) {
                if (u.getLogin() != null && u.getPassword() != null) {
                    if (u.getLogin().equalsIgnoreCase(login) && u.getPassword().equals(password)) {
                        session.setAttribute(Constant.USER, u);
                        userFound = true;
                        break;
                    } else if (u.getLogin().equalsIgnoreCase(login) && !u.getPassword().equals(password)) {
                        String message = "Wrong password";
                        log.warn(message);
                        session.setAttribute(Constant.ERROR_MESSAGE, message);
                        return Go.LOGIN;
                    }
                }
            }
            if (!userFound) {
                String message = String.format("The user %s not exist", login);
                log.warn(message);
                req.getSession().setAttribute(Constant.ERROR_MESSAGE, message);
                return Go.LOGIN;
            }
        return Go.HOME;
    }
}
