package com.javarush.cmd;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.util.Constant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class ListUserIT extends BaseIT {
    private final ListUser listUser = Winter.find(ListUser.class);

    @Test
    @DisplayName("do get redirect to list-user")
    void doGetRedirectToListUser() {
        String actualRedirect = listUser.doGet(request);
        Assertions.assertEquals("list-user", actualRedirect);
        verify(request).setAttribute(eq(Constant.USERS), any(Collection.class));
    }

}