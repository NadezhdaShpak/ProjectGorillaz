package com.javarush.cmd;

import com.javarush.entity.User;
import com.javarush.exception.AppException;
import com.javarush.service.ValidateService;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
public class Login implements Command {
    private final ValidateService validateService;
    private static final Logger log = LogManager.getLogger(Login.class);



    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String login = req.getParameter(Constant.LOGIN);
        String password = req.getParameter(Constant.PASSWORD);

        try {
            User user = validateService.validate(login, password);
            session.setAttribute(Constant.USER, user);
        }
        catch (AppException e) {
            log.warn(e.getMessage());
            req.getSession().setAttribute(Constant.ERROR_MESSAGE, e.getMessage());
            return Go.LOGIN;
        }
        return Go.HOME;
    }
}
