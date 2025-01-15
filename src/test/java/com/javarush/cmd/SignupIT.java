package com.javarush.cmd;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.entity.User;
import com.javarush.exception.AppException;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SignupIT extends BaseIT {
    private final Signup signup = Winter.find(Signup.class);

    @Test
    @DisplayName("When signup correctly create new user and redirect to profile")
    void WhenSignupCorrectlyCreateNewUserAndRedirectToProfile() {
        when(request.getParameter(Constant.LOGIN)).thenReturn("newUser");
        when(request.getParameter(Constant.PASSWORD)).thenReturn("admin");
        when(request.getParameter(Constant.ROLE)).thenReturn("GUEST");

        String actualRedirect = signup.doPost(request);
        Assertions.assertTrue(userRepository.getAll().toString().contains("newUser"));
        assertTrue(actualRedirect.startsWith(Go.PROFILE));
        verify(session).setAttribute(eq(Constant.USER), any(User.class));
    }
    @Test
    @DisplayName("When login exist throw exception")
    void WhenLoginExistThrowException() {
        when(request.getParameter(Constant.LOGIN)).thenReturn("Carl");
        when(request.getParameter(Constant.PASSWORD)).thenReturn("123");
        Assertions.assertThrows(AppException.class,() -> signup.doPost(request));
    }

}