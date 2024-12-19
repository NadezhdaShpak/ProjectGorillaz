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
public class EditUser implements Command {

    private final UserService userService;
    private final ImageService imageService;


    @Override
    public String doGet(HttpServletRequest req) {
        String stringId = req.getParameter("id");
        if (stringId != null) {
            long id = Long.parseLong(stringId);
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
        long id = Long.parseLong(req.getParameter("id"));
        User currentUser = (User) req.getSession().getAttribute("user");
        User user;
        if (currentUser.getRole() == Role.ADMIN) {
            user = User.builder()
                    .id(id)
                    .login(req.getParameter("login"))
                    .password(req.getParameter("password"))
                    .role(Role.valueOf(req.getParameter("role")))
                    .build();
        }
        else {
            user = User.builder()
                    .id(id)
                    .login(req.getParameter("login"))
                    .password(req.getParameter("password"))
                    .role(currentUser.getRole())
                    .build();
        }
        userService.update(user);

        imageService.uploadImage(req, user.getImage());

        return getView() + "?id=" + user.getId();
    }


}