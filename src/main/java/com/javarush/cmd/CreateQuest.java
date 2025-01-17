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

@AllArgsConstructor
public class CreateQuest implements Command {
    private static final Logger log = LogManager.getLogger(CreateQuest.class);
    private final ImageService imageService;
    private QuestService questService;


    public String doPost(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User currentUser = (User) req.getSession().getAttribute(Constant.USER);

        String questsName = req.getParameter(Constant.NAME);
        String description = req.getParameter(Constant.DESCRIPTION);
        String winMessage = req.getParameter(Constant.WIN_MESSAGE);
        String looseMessage = req.getParameter(Constant.LOOSE_MESSAGE);

        if (questsName == null || description == null || winMessage == null || looseMessage == null) {
            req.setAttribute("errorMessage", "Все поля должны быть заполнены");
        }
        ArrayList<Question> questsQuestions = new ArrayList<>();
        int i = 1;
        while (req.getParameter(Constant.QUESTION + i) != null) {
            String questionText = req.getParameter(Constant.QUESTION + i);
            ArrayList<Answer> answers = new ArrayList<>();
            answers.add(new Answer(req.getParameter(Constant.ANSWER_WIN + i), true, 1L));
            answers.add(new Answer(req.getParameter(Constant.ANSWER_LOOSE + i), false, 2L));
            questsQuestions.add(new Question(questionText, answers, Long.valueOf(i)));
            i++;
        }

        Quest quest = Quest.builder()
                .name(questsName)
                .description(description)
                .authorId(currentUser.getId())
                .questions(questsQuestions)
                .winMessage(winMessage)
                .looseMessage(looseMessage)
                .build();
        questService.create(quest);
        log.info("Created new quest {}", quest.getName());
        try {
            imageService.uploadImage(req, quest.getImage());
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
        session.setAttribute(Constant.QUEST, quest);
        return Go.EDIT_QUEST + "?id=" + quest.getId();
    }
}
