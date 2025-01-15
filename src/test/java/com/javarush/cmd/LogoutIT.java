package com.javarush.cmd;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.util.Go;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

class LogoutIT extends BaseIT {
    private final Logout logout = Winter.find(Logout.class);

    @Test
    @DisplayName("do get redirect to home")
    void doGetRedirectToHome() {
        String actualRedirect = logout.doGet(request);
        Assertions.assertEquals(Go.HOME, actualRedirect);
    }

    @Test
    @DisplayName("when logout then session invalidate")
    void whenLogoutThenSessionInvalidate() {
        logout.doGet(request);
        verify(session).invalidate();
    }
}