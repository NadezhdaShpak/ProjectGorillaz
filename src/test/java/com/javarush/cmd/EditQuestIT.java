package com.javarush.cmd;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.entity.Quest;
import com.javarush.util.Constant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EditQuestIT extends BaseIT {
    private final EditQuest editQuest = Winter.find(EditQuest.class);

    @Test
    @DisplayName("do get redirect to edit-quest")
    void doGetRedirectToListQuest() {
        Quest quest = questService.getAll().stream().findFirst().orElseThrow();
        when(request.getParameter(Constant.ID)).thenReturn(quest.getId().toString());
        String actualRedirect = editQuest.doGet(request);
        Assertions.assertEquals("edit-quest", actualRedirect);
        verify(request).setAttribute(eq(Constant.QUEST), eq(quest));
    }
    @Test
    @DisplayName("When update changes name")
    void whenUpdateChangesName() {
        Quest quest = questService.getAll().stream().findFirst().orElseThrow();
        when(request.getParameter(Constant.ID)).thenReturn(quest.getId().toString());
        when(request.getParameter(Constant.NAME)).thenReturn("testQuest");
        when(request.getParameter(Constant.DESCRIPTION)).thenReturn("testDescription");
        when(request.getParameter(Constant.WIN_MESSAGE)).thenReturn("testWinMessage");
        when(request.getParameter(Constant.LOOSE_MESSAGE)).thenReturn("testLooseMessage");
        when(request.getParameter(Constant.QUESTION + 1)).thenReturn("testQuestion");
        when(request.getParameter(Constant.ANSWER_WIN + 1)).thenReturn("testWinAnswer");
        when(request.getParameter(Constant.ANSWER_LOOSE + 1)).thenReturn("testLooseAnswer");
        editQuest.doPost(request);

        Quest questAfterUpdate = questService.getAll().stream().findFirst().orElseThrow();
        Assertions.assertEquals("testQuest", questAfterUpdate.getName());
    }
}