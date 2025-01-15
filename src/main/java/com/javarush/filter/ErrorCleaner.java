package com.javarush.filter;

import com.javarush.util.Constant;
import com.javarush.util.Go;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter({Go.INDEX, Go.HOME,
        Go.SIGNUP, Go.LOGIN, Go.LOGOUT, Go.EDIT_QUEST,
        Go.LIST_USER, Go.PROFILE, Go.EDIT_USER,
        Go.CREATE, Go.QUEST, Go.PLAY_GAME, Go.STATISTICS})
public class ErrorCleaner extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(request, response);
        HttpSession session = request.getSession(false);
        if (request.getMethod().equals("GET") && session != null) {
session.removeAttribute(Constant.ERROR_MESSAGE);
        }

    }
}
