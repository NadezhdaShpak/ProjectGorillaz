package com.javarush.cmd;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.entity.Quest;
import com.javarush.entity.User;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateQuestIT extends BaseIT {
    private final CreateQuest createQuest = Winter.find(CreateQuest.class);

    @Test
    @DisplayName("When do post correct redirect to edit-quest")
    void WhenDoPostCorrectRedirectToEditQuest() {
        User user = userservice.getAll().stream().findFirst().orElseThrow();
        when(request.getSession().getAttribute(Constant.USER)).thenReturn(user);

        when(request.getParameter(Constant.NAME)).thenReturn("testQuest");
        when(request.getParameter(Constant.DESCRIPTION)).thenReturn("testDescription");
        when(request.getParameter(Constant.WIN_MESSAGE)).thenReturn("testWinMessage");
        when(request.getParameter(Constant.LOOSE_MESSAGE)).thenReturn("testLooseMessage");

        when(request.getParameter(Constant.QUESTION + 1)).thenReturn("testQuestion");
        when(request.getParameter(Constant.ANSWER_WIN + 1)).thenReturn("testWinAnswer");
        when(request.getParameter(Constant.ANSWER_LOOSE + 1)).thenReturn("testLooseAnswer");

        String actualRedirect = createQuest.doPost(request);
        assertTrue(actualRedirect.startsWith(Go.EDIT_QUEST));
    }

    @Test
    @DisplayName("When do post correct create new Quest")
    void WhenDoPostCorrectCreateNewQuest() {
        User user = userservice.getAll().stream().findFirst().orElseThrow();
        when(request.getSession().getAttribute(Constant.USER)).thenReturn(user);
        when(request.getParameter(Constant.NAME)).thenReturn("testQuest");
        when(request.getParameter(Constant.DESCRIPTION)).thenReturn("testDescription");
        when(request.getParameter(Constant.WIN_MESSAGE)).thenReturn("testWinMessage");
        when(request.getParameter(Constant.LOOSE_MESSAGE)).thenReturn("testLooseMessage");
        when(request.getParameter(Constant.QUESTION + 1)).thenReturn("testQuestion");
        when(request.getParameter(Constant.ANSWER_WIN + 1)).thenReturn("testWinAnswer");
        when(request.getParameter(Constant.ANSWER_LOOSE + 1)).thenReturn("testLooseAnswer");

        createQuest.doPost(request);
        assertTrue(questRepository.getAll().toString().contains("testQuest"));
        verify(session).setAttribute(eq(Constant.QUEST), any(Quest.class));
    }
}