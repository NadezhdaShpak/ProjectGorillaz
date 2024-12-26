package com.javarush.cmd;

import com.javarush.entity.Role;
import com.javarush.entity.User;
import com.javarush.service.ImageService;
import com.javarush.service.UserService;
import com.javarush.util.Constant;
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
        String stringId = req.getParameter(Constant.ID);
        if (stringId != null) {
            long id = Long.parseLong(stringId);
            Optional<User> optionalUser = userService.get(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                req.setAttribute(Constant.USER, user);
            }
        }
        return getView();
    }

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter(Constant.ID));
        User currentUser = (User) req.getSession().getAttribute(Constant.USER);
        User user;
        if (currentUser.getRole() == Role.ADMIN) {
            user = User.builder()
                    .id(id)
                    .login(req.getParameter(Constant.LOGIN))
                    .password(req.getParameter(Constant.PASSWORD))
                    .role(Role.valueOf(req.getParameter(Constant.ROLE)))
                    .build();
        }
        else {
            user = User.builder()
                    .id(id)
                    .login(req.getParameter(Constant.LOGIN))
                    .password(req.getParameter(Constant.PASSWORD))
                    .role(currentUser.getRole())
                    .build();
        }
        userService.update(user);

        imageService.uploadImage(req, user.getImage());

        return getView() + "?id=" + user.getId();
    }


}