package com.javarush.cmd;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.entity.Role;
import com.javarush.entity.User;
import com.javarush.util.Constant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EditUserIT extends BaseIT {

    private final EditUser editUser = Winter.find(EditUser.class);
    @Test
    @DisplayName("do get redirect to edit-user")
    void doGetRedirectToListUser() {
        User user = userservice.getAll().stream().findFirst().orElseThrow();
        when(request.getParameter(Constant.ID)).thenReturn(user.getId().toString());
        String actualRedirect = editUser.doGet(request);
        Assertions.assertEquals("edit-user", actualRedirect);
        verify(request).setAttribute(eq(Constant.USER), eq(user));
    }

    @Test
    @DisplayName("When update changes login")
    void whenUpdateChangesLogin() {
        User user = userservice.getAll().stream().findFirst().orElseThrow();
        when(request.getParameter(Constant.ID)).thenReturn("1");
        when(request.getSession().getAttribute(Constant.USER)).thenReturn(user);
        when(request.getParameter(Constant.LOGIN)).thenReturn("newUser");
        when(request.getParameter(Constant.PASSWORD)).thenReturn("123");
        when(request.getParameter(Constant.ROLE)).thenReturn(Role.ADMIN.name());
        editUser.doPost(request);
        Assertions.assertTrue(userRepository.getAll().toString().contains("newUser")); //* _ *//
        String actualLogin = userservice.getAll().stream().findFirst().orElseThrow().getLogin();
        Assertions.assertEquals("newUser", actualLogin);
    }

    @Test
    @DisplayName("When user update role no changes name")
    void whenUserUpdateRoleNoChanges() {
        User user = userservice.getAll().stream().findFirst().orElseThrow();
        when(request.getParameter(Constant.ID)).thenReturn("1");
        when(request.getSession().getAttribute(Constant.USER)).thenReturn(user);
        when(request.getParameter(Constant.ROLE)).thenReturn(Role.ADMIN.name());

        editUser.doPost(request);
        Role actualRole = userservice.getAll().stream().findFirst().orElseThrow().getRole();
        Assertions.assertEquals("USER", actualRole.name());


    }
}