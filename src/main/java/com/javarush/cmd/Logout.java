package com.javarush.cmd;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

public class Logout implements Command {

    @Override
    @SneakyThrows
    public String doGet(HttpServletRequest req) {
        req.getSession().invalidate();
        return "/home";
    }
}
