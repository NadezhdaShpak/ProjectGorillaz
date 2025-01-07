package com.javarush.controller;

import com.javarush.cmd.Command;
import com.javarush.config.Winter;
import com.javarush.entity.Role;
import com.javarush.util.Go;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({Go.INDEX, Go.HOME,
        Go.SIGNUP, Go.LOGIN, Go.LOGOUT, Go.EDIT_QUEST,
        Go.LIST_USER, Go.PROFILE, Go.EDIT_USER,
        Go.CREATE, Go.QUEST, Go.PLAY_GAME})
@MultipartConfig (fileSizeThreshold = 1 << 20)
public class FrontController extends HttpServlet {

    private final HttpResolver httpResolver = Winter.find(HttpResolver.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = httpResolver.resolve(req);
        String view = command.doGet(req);
        String jsp = getJsp(view);
        req.getRequestDispatcher(jsp).forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) {
        config.getServletContext().setAttribute("roles", Role.values());
    }

    private static String getJsp(String view) {
        return "/WEB-INF/" + view + ".jsp";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = httpResolver.resolve(req);
        String redirect = command.doPost(req);
        resp.sendRedirect(redirect);
    }
}
