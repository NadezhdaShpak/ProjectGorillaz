package com.javarush.cmd;

import com.javarush.entity.Answer;
import com.javarush.entity.Quest;
import com.javarush.entity.Question;
import com.javarush.entity.User;
import com.javarush.service.ImageService;
import com.javarush.service.QuestService;
import com.javarush.util.Constant;
import com.javarush.util.Go;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CreateQuest implements Command {
    private static final Logger log = LogManager.getLogger(CreateQuest.class);
    private final ImageService imageService;
    private QuestService questService;


    public String doPost(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User currentUser = (User) req.getSession().getAttribute(Constant.USER);

        String questsName = req.getParameter("name");
        String description = req.getParameter("description");

//        Long id = (Long) session.getAttribute(Constant.ID);
        String winMessage = req.getParameter("winMessage");
        String looseMessage = req.getParameter("looseMessage");

        // Determine the number of questions


        Collection<Question> questsQuestions = new ArrayList<>();
        int i = 1;
        while (req.getParameter(Constant.QUESTION + i) != null) {
            log.info("question{} is empty {}", i, req.getParameter(Constant.QUESTION + i).isEmpty());
            String questionText = req.getParameter(Constant.QUESTION + i);
            log.info("question is {}", questionText);

            ArrayList<Answer> answers = new ArrayList<>();
            answers.add(new Answer(req.getParameter(Constant.ANSWER_WIN + i), true, 1L));
            log.info("answer win is {}", req.getParameter(Constant.ANSWER_WIN + i));
            answers.add(new Answer(req.getParameter(Constant.ANSWER_LOOSE + i), false, 2L));
            log.info("answer loose is {}", req.getParameter(Constant.ANSWER_LOOSE + i));
            questsQuestions.add(new Question(questionText, answers, Long.valueOf(i)));
            i++;
        }
        questsQuestions.forEach(question -> {
            log.info(question.getText());
            log.info(question.getAnswers().toArray());
            System.out.println();
        });
        Quest quest = Quest.builder()
                .name(questsName)
                .description(description)
                .authorId(currentUser.getId())
                .questions(questsQuestions)
                .winMessage(winMessage)
                .looseMessage(looseMessage)
                .build();
        questService.create(quest);
        log.info("{} created", quest.getName());
        try {
            imageService.uploadImage(req, quest.getImage());
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
        log.info("questions {}", quest.getQuestions().toArray());

        session.setAttribute(Constant.QUEST, quest);

        return Go.QUEST + "?id=" + quest.getId();
    }
}
