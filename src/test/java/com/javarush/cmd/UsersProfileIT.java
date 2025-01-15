package com.javarush.cmd;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.entity.Role;
import com.javarush.entity.User;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsersProfileIT extends BaseIT {
    private final UsersProfile usersProfile = Winter.find(UsersProfile.class);
    @Test
    @DisplayName("do get redirect to users-profile")
    void doGetRedirectToUsersProfile() {
        String actualRedirect = usersProfile.doGet(request);
        Assertions.assertEquals("users-profile", actualRedirect);
    }

    @Test
    @DisplayName("When do post then redirect to exact usersProfile")
    void whenDoPostThenRedirectToExactUsersProfile() {
        when(request.getParameter(Constant.LOGIN)).thenReturn("Carl");
        when(request.getParameter(Constant.ROLE)).thenReturn(Role.ADMIN.name());
        String actualRedirect = usersProfile.doPost(request);
        Assertions.assertEquals("users-profile?id=null", actualRedirect);
    }


}