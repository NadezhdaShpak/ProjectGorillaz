package com.javarush.service;

import com.javarush.BaseIT;
import com.javarush.config.Winter;
import com.javarush.entity.Game;
import com.javarush.entity.GameState;
import com.javarush.entity.Question;
import com.javarush.entity.User;
import com.javarush.repository.GameRepository;
import com.javarush.repository.QuestRepository;
import com.javarush.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class GameServiceTest extends BaseIT {
    private final GameService gameService = Winter.find(GameService.class);

    @Test
    void checkWin() {

        Game game = new Game();
        User user = userRepository.get(3L);
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question());
        questions.add(new Question());

        // Act
        gameService.checkWin(1L, 1L, questions, game, user);

        // Assert
        assertEquals(GameState.WIN, game.getGameState());
        assertEquals(1, user.getNumberOfWinGames());
    }
}