package com.javarush.service;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class GameServiceTest extends BaseIT {
    private final GameService gameService = Winter.find(GameService.class);

    @Test
    @DisplayName("When the answer is correct, the game state is win")
    void checkWin() {
        ArrayList<Question> questions = new ArrayList<>();
        ArrayList<Answer> answers = new ArrayList<>();
        answers.add(new Answer("test Answer", true, 1L));
        questions.add(new Question("text", answers, 1L));
        User user = userRepository.get(2L);
        Quest quest = Quest.builder().id(5L)
                .name("test name")
                .author(user)
                .questions(questions)
                .winMessage("test Win message")
                .looseMessage("test Loose message")
                .description("test desc")
                .build();
        questService.create(quest);
        Game game = gameService.getGame(quest.getId(), user.getId()).get();
        questService.create(quest);
        // Act
        gameService.checkWin(1L, 1L, questions, game, user);

        // Assert
        assertEquals(GameState.WIN, game.getGameState());
        assertEquals(1, user.getNumberOfWinGames());
    }
}