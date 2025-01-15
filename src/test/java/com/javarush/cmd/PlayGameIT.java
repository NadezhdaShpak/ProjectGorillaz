package com.javarush.cmd;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.entity.Game;
import com.javarush.entity.Quest;
import com.javarush.entity.User;
import com.javarush.exception.AppException;
import com.javarush.service.GameService;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlayGameIT extends BaseIT {

    private final PlayGame playGame = Winter.find(PlayGame.class);

    @Disabled
    @Test
    @DisplayName("When play game then redirect to play-game")
    void whenPlayGameThenRedirectToPlayGame() {
        User user = userservice.getAll().stream().findFirst().orElseThrow();
        Quest quest = questService.getAll().stream().findFirst().orElseThrow();
        when(request.getSession().getAttribute(Constant.USER)).thenReturn(user);
        when(request.getParameter(Constant.QUEST_ID)).thenReturn(quest.getId().toString());
        String actualRedirect = playGame.doGet(request);
        Assertions.assertEquals("play-game", actualRedirect);
        verify(session).setAttribute(eq(Constant.GAME), any(Game.class));
        verify(session).setAttribute(eq(Constant.QUEST), any(Quest.class));
    }
    @Test
    @DisplayName("When user is null then redirect to login")
    void whenUserIsNullThenRedirectToLogin() {
        Quest quest = questService.getAll().stream().findFirst().orElseThrow();
        when(request.getParameter(Constant.QUEST_ID)).thenReturn(quest.getId().toString());
        String actualRedirect = playGame.doGet(request);
        Assertions.assertEquals(Go.LOGIN, actualRedirect);
    }
@Disabled
    @Test
    @DisplayName("When no answer is selected, then throw AppException")
    void whenNoAnswerIsSelectedThenThrowAppException() {
        User user = userservice.getAll().stream().findFirst().orElseThrow();
        Quest quest = questService.getAll().stream().findFirst().orElseThrow();
        Game game = gameService.getGame(quest.getId(), user.getId()).get();
        when(request.getSession().getAttribute(Constant.USER)).thenReturn(user);
        when(request.getParameter(Constant.QUEST_ID)).thenReturn(quest.getId().toString());
        when(request.getSession().getAttribute(Constant.GAME)).thenReturn(game);
        when(request.getSession().getAttribute(Constant.QUESTION)).thenReturn(quest.getQuestions().getFirst());

        Assertions.assertThrows(AppException.class,() -> playGame.doPost(request));
    }
    @Disabled
    @Test
    @DisplayName("When the answer is wrong, the game is lost")
    void whenTheAnswerIsWrongTheGameIsLost() {
        User user = userservice.getAll().stream().findFirst().orElseThrow();
        Quest quest = questService.getAll().stream().findFirst().orElseThrow();
        Game game = gameService.getGame(quest.getId(), user.getId()).get();
        when(request.getSession().getAttribute(Constant.GAME)).thenReturn(game);
        when(request.getSession().getAttribute(Constant.QUEST)).thenReturn(quest);
        when(request.getSession().getAttribute(Constant.QUESTION)).thenReturn(quest.getQuestions().getFirst());
        when(request.getParameter(Constant.QUEST_ID)).thenReturn(quest.getId().toString());
        when(request.getParameter(Constant.ANSWER)).thenReturn("2");
        String gameState = "LOSE";
        playGame.doPost(request);
        Assertions.assertEquals(gameState, game.getGameState().name());
    }
}