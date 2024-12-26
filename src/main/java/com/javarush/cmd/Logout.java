package com.javarush.cmd;

import com.javarush.util.Go;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

public class Logout implements Command {

    @Override
    @SneakyThrows
    public String doGet(HttpServletRequest req) {
        req.getSession().invalidate();
        return Go.HOME;
    }
}
