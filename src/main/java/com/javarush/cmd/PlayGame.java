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

import java.util.*;

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
        } else {
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
                        req.setAttribute(Constant.GAME, newGame);
                        showOneQuestion(req, newGame);
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
        Question question = (Question) session.getAttribute(Constant.QUESTION);
        Long currentQuestionId = question.getId();
        String selectedAnswerId = request.getParameter(Constant.ANSWER);

        long answerId = request.getParameter(Constant.ANSWER) == null ? 0 : Long.parseLong(request.getParameter(Constant.ANSWER));

        Optional<Game> game = gameService.processOneStep(gameId, currentQuestionId, answerId);

        Long nextQuestionId = currentQuestionId + 1;

        Optional<Question> nextQuestion = reqGame.getQuest().getQuestions().stream().filter(q -> Objects.equals(q.getId(), nextQuestionId)).findFirst();
        Question currQuestion = nextQuestion.get();
        List<Answer> answers = (List<Answer>) currQuestion.getAnswers();
        Collections.shuffle(answers);
        request.getSession().setAttribute(Constant.QUESTION, new Question(currQuestion.getText(), answers, currQuestion.getId()));
        if (game.isPresent()) {
            Game currentGame = game.get();
            if (answerId == 0 && request.getParameter(Constant.GAME) != null) {
                String message = "Нужно выбрать какой-то ответ";
                log.warn(message);
                request.getSession().setAttribute(Constant.ERROR_MESSAGE, message);
                --currentQuestionId;
            } else if (currentGame.getGameState() == GameState.LOSE) {
                request.getSession().setAttribute(Constant.GAME, currentGame);
            } else if (currentGame.getGameState() == GameState.WIN) {

            }
            return "%s?questId=%d&id=%d".formatted(Go.PLAY_GAME, reqQuest.getId(), game.get().getId());
        } else {
            String message = "Нет такой игры";
            log.warn(message);
            request.getSession().setAttribute(Constant.ERROR_MESSAGE, message);
            return Go.HOME;
        }
    }


    private void showOneQuestion(HttpServletRequest request, Game game) {
        request.getSession().setAttribute(Constant.GAME, game);
        Long questID = game.getQuest().getId();
        int currentQuestionId = game.getCurrentQuestionId().intValue();
        Quest quest = questService.get(questID).get();

        Optional<Question> question = quest.getQuestions().stream().filter(q -> q.getId() == currentQuestionId).findFirst();
        Question currQuestion = question.get();
        List<Answer> answers = (List<Answer>) currQuestion.getAnswers();
        Collections.shuffle(answers);
        request.getSession().setAttribute(Constant.QUESTION, new Question(currQuestion.getText(), answers, currQuestion.getId()));
        request.getSession().setAttribute(Constant.QUEST, quest);

    }

}
