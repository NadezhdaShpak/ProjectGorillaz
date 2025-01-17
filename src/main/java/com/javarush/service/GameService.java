package com.javarush.service;

import com.javarush.entity.*;
import com.javarush.exception.AppException;
import com.javarush.repository.GameRepository;
import com.javarush.repository.QuestRepository;
import com.javarush.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final QuestRepository questRepository;
    private final UserRepository userRepository;

    public Optional<Game> getGame(Long questId, Long userId) {
        Game gamePattern = Game.builder().questId(questId).build();
        gamePattern.setUserId(userId);
        Optional<Game> currentGame = gameRepository
                .find(gamePattern)
                .max(Comparator.comparingLong(Game::getId));
        if (currentGame.isPresent()) {
            return currentGame;
        } else if (gamePattern.getQuestId() != null) {
            return Optional.of(getNewGame(gamePattern.getQuestId(), userId));
        } else {
            return Optional.empty();
        }
    }

    public Game getNewGame(Long questId, Long userId) {
        Quest quest = questRepository.get(questId);

        Question firstQuestion = quest.getQuestions().getFirst();
        Game newGame = Game.builder()
                .quest(quest)
                .currentQuestionId(firstQuestion.getId())
                .gameState(GameState.PLAYING)
                .userId(userId) //from session
                .build();
        gameRepository.create(newGame);
        return newGame;
    }

    public Optional<Game> processOneStep(Long gameId, Long questionId, Long answerId) {
        Game game = gameRepository.get(gameId);
        ArrayList<Question> questions = game.getQuest().getQuestions();
        User user = userRepository.get(game.getUserId());
        if (game.getGameState() == GameState.PLAYING) {
            checkWin(questionId, answerId, questions, game, user);

        } else {
            game = getNewGame(game.getUserId(), game.getQuestId());
        }
        return Optional.ofNullable(game);
    }

    public void checkWin(Long questionId, Long answerId, ArrayList<Question> questions, Game game, User user) {
        if (answerId == 1 && questionId.intValue() == (questions.size() - 1)) {
            game.setGameState(GameState.WIN);
            user.setNumberOfWinGames(user.getNumberOfWinGames() + 1);
            userRepository.update(user);
        }
        else if (answerId == 2) {
            game.setGameState(GameState.LOSE);
            user.setNumberOfLooseGames(user.getNumberOfLooseGames() + 1);
            userRepository.update(user);
        }
        else if (answerId == 0) {
            throw new AppException("Нужно выбрать какой-то ответ");
        }
        else {
            game.setCurrentQuestionId(++questionId);
            gameRepository.update(game);
        }
    }

}