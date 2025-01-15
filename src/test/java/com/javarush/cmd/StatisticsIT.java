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

class StatisticsIT extends BaseIT {
    private final Statistics statistics = Winter.find(Statistics.class);

    @Test
    @DisplayName("do get redirect to statistics")
    void doGetRedirectToStatistics() {
        String actualRedirect = statistics.doGet(request);
        Assertions.assertEquals("statistics", actualRedirect);
        verify(request).setAttribute(eq(Constant.USERS), any(Collection.class));
    }
}