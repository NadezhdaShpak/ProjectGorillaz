package com.javarush.cmd;

import com.javarush.entity.*;
import com.javarush.service.GameService;
import com.javarush.service.QuestService;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import jakarta.servlet.http.HttpServletRequest;
//
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("unused")
@AllArgsConstructor
public class PlayGame implements Command {
    private final QuestService questService;
    private final GameService gameService;
    private static final Logger log = LogManager.getLogger(PlayGame.class);


    @Override
    public String doGet(HttpServletRequest req) {
        Game reqGame = (Game) req.getSession().getAttribute(Constant.GAME);
        if (reqGame != null) {
            Quest quest = (Quest) req.getSession().getAttribute(Constant.QUEST);
            req.setAttribute(Constant.GAME, reqGame);
        for (Question question : quest.getQuestions()) {
            if (Objects.equals(question.getId(), reqGame.getCurrentQuestionId())) {
                req.getSession().setAttribute(Constant.QUESTION, question);
            }
        }
            return getView();
        }
        else {
            User currentUser = (User) req.getSession().getAttribute(Constant.USER);
            long userId;
            if (currentUser != null) {
                userId = currentUser.getId();
                String stringId = req.getParameter(Constant.QUEST_ID);
                if (stringId != null) {
                    long questId = Long.parseLong(stringId);
                    Optional<Quest> optionalQuest = questService.get(questId);
                    if (optionalQuest.isPresent()) {
                        Quest quest = optionalQuest.get();
                        req.setAttribute(Constant.QUEST, quest);

                        Optional<Game> optionalGame = gameService.getGame(questId, userId);
                        Game newGame = optionalGame.get();
                        log.info("48 created new game");
                        req.setAttribute(Constant.GAME, newGame);
                        showOneQuestion(req, newGame);

//                    userRepository.get(userId).getGames().add(newGame);
                    }
                }
            } else {
                return Go.LOGIN;
            }
        }
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Game reqGame = (Game) session.getAttribute(Constant.GAME);


        Quest reqQuest = (Quest) session.getAttribute(Constant.QUEST);
        Long gameId = reqGame.getId();
        log.info("70 gameID" + gameId);
        Question question = (Question) session.getAttribute(Constant.QUESTION);
        Long currentQuestionId = question.getId();
        log.info("73 currentQuestionId " + currentQuestionId);
        String selectedAnswerId = request.getParameter("answer");
        Long answerId = request.getParameter(Constant.ANSWER) == null ? 0 : Long.parseLong(request.getParameter(Constant.ANSWER));
        Optional<Game> game = gameService.processOneStep(gameId, currentQuestionId, answerId);
        if (game.isPresent()) {
            Game currentGame = game.get();
            if (answerId == 0 && request.getParameter(Constant.GAME) != null) {
                String message = "Нужно выбрать какой-то ответ";
                log.warn(message);
                --currentQuestionId;
//                RequestHelpers.createError(request, message);
            }
            else if (currentGame.getGameState() == GameState.LOSE) {
                request.getSession().setAttribute(Constant.GAME, currentGame);
            }
            else if (currentGame.getGameState() == GameState.WIN) {

            }
            return "%s?questId=%d&id=%d".formatted(Go.PLAY_GAME, reqQuest.getId(), game.get().getId());
        } else {
            String message = "Нет такой игры";
            log.warn(message);
//            RequestHelpers.createError(request, message);
            return Go.HOME;
        }
    }


    private void showOneQuestion(HttpServletRequest request, Game game) {
        request.getSession().setAttribute(Constant.GAME, game);
        Long questID = game.getQuest().getId();
        int currentQuestionId = game.getCurrentQuestionId().intValue();
        Quest quest = questService.get(questID).get();
//        for (Question question : quest.getQuestions()) {
//            if (question.getId() == currentQuestionId) {
//                request.getSession().setAttribute(Constant.QUESTION, question);
//            }
//        }
        Optional<Question> question = quest.getQuestions().stream().filter(q -> q.getId() == currentQuestionId).findFirst();
        request.getSession().setAttribute(Constant.QUESTION, question.orElseThrow());
        log.info("108 ID = {}, {}", question.get().getId(), question);
        request.getSession().setAttribute(Constant.QUEST, quest);

    }

}
