package com.javarush.cmd;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.entity.Quest;
import com.javarush.entity.User;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HomeIT extends BaseIT {
    private final Home home = Winter.find(Home.class);

    @Test
    @DisplayName("do get redirect to home")
    void doGetRedirectToHome() {
        String actualRedirect = home.doGet(request);
        Assertions.assertEquals("home", actualRedirect);
        verify(request).setAttribute(eq(Constant.QUESTS), any(Collection.class));
    }
}