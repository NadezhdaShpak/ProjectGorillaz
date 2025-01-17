package com.javarush.filter;

import com.javarush.exception.AppException;
import com.javarush.util.Go;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebFilter({Go.INDEX, Go.HOME,
         Go.LOGOUT, Go.EDIT_QUEST,
        Go.LIST_USER, Go.PROFILE, Go.EDIT_USER,
        Go.CREATE, Go.QUEST})
public class CsrfFilter extends HttpFilter {

    public static final String CSRF = "csrf";
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getMethod().equals("GET")) {
            addCsrf(req, res);
        }
        else if (req.getMethod().equals("POST")) {
            checkCsrf(req, res);
        }
        super.doFilter(req, res, chain);
    }
    private static void addCsrf(HttpServletRequest req, HttpServletResponse res) {
        String csrf = UUID.randomUUID().toString();
        req.getSession().setAttribute(CSRF, csrf);
        Cookie cookie = new Cookie(CSRF, csrf);
        cookie.setMaxAge(15*60);
        res.addCookie(cookie);
    }

    private static void checkCsrf(HttpServletRequest req, HttpServletResponse res) {

        String storedCsrf = (String) req.getSession().getAttribute(CSRF);
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals(CSRF) && cookie.getValue().equals(storedCsrf)) {
                return;
            } else {
                throw new AppException("Invalid csrf token");
            }
        }
    }
}
