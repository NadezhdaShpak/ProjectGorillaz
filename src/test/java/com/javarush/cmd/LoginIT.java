package com.javarush.cmd;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.entity.User;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LoginIT extends BaseIT {

    private final Login login = Winter.find(Login.class);

    @Test
    @DisplayName("When correct login then redirect to home")
    void whenCorrectLoginThenRedirectToHome() {
        when(request.getParameter(Constant.LOGIN)).thenReturn("Carl");
        when(request.getParameter(Constant.PASSWORD)).thenReturn("admin");

        String actualRedirect = login.doPost(request);
        Assertions.assertEquals(Go.HOME, actualRedirect);
        verify(session).setAttribute(eq(Constant.USER), any(User.class));
    }

    @Test
    @DisplayName("When incorrect login then redirect to login")
    void whenIncorrectLoginThenRedirectToLogin() {
        when(request.getParameter(Constant.LOGIN)).thenReturn("Carl123");
        when(request.getParameter(Constant.PASSWORD)).thenReturn("err");

        String actualRedirect = login.doPost(request);
        Assertions.assertEquals(Go.LOGIN, actualRedirect);
    }
}