package com.javarush.service;

import com.javarush.cmd.PlayGame;
import com.javarush.entity.*;
import com.javarush.repository.GameRepository;
import com.javarush.repository.QuestRepository;
import com.javarush.repository.Repository;
import com.javarush.util.Constant;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final QuestRepository questRepository;
    private static final Logger log = LogManager.getLogger(GameService.class);


//
//    public GameService(GameRepository gameRepository) {
//        this.gameRepository = gameRepository;
//    }
//    private final Repository<Question> questionRepository;
//    private final Repository<Answer> answerRepository;

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
//        userRepository.get(userId).getGames().add(newGame);
        gameRepository.create(newGame);
        return newGame;
    }



    public Optional<Game> processOneStep(Long gameId, Long questionId, Long answerId) {
        Game game = gameRepository.get(gameId);
        log.info("gameID = {}", game.getId());
        ArrayList<Question> questions = game.getQuest().getQuestions();
        log.info("questionId = {}", questionId);
//        for (Question question : questions) {
//            log.info("question #{} = {}", question.getId(), question);
//        }
        log.info("questions.size() " + questions.size());
        if (game.getGameState() == GameState.PLAYING) {
            if (answerId == 1 && questionId.intValue() == (questions.size() - 1)) {
                game.setGameState(GameState.WIN);
            }
            else if (answerId == 2) {
                game.setGameState(GameState.LOSE);
            }
            else if (answerId == 0) {

            }
            else {
                game.setCurrentQuestionId(++questionId);
                gameRepository.update(game);
            }

        } else {
            game = getNewGame(game.getUserId(), game.getQuestId());
        }
        return Optional.ofNullable(game);
    }

}