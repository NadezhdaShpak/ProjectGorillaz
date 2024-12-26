package com.javarush.cmd;

import com.javarush.entity.Answer;
import com.javarush.entity.Quest;
import com.javarush.entity.Question;
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

    public String doGet(HttpServletRequest request)  {
        List<String> questions = (List<String>) request.getSession().getAttribute("questsQuestions");
        if (questions == null) {
            questions = new ArrayList<>();
            request.getSession().setAttribute("questsQuestions", questions);
        }

        // Подготовка данных для отображения на JSP странице
        request.setAttribute("questions", questions);

        return Go.CREATE;
    }


    public String doPost(HttpServletRequest req)  {
        HttpSession session = req.getSession();

        String questsName = req.getParameter("name");
        String description = req.getParameter("description");
        try {
            imageService.uploadImage(req, req.getParameter("image"));
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
//        Long id = (Long) session.getAttribute(Constant.ID);
        String winMessage = req.getParameter("winMessage");
        String looseMessage = req.getParameter("looseMessage");

        // Determine the number of questions
        int numQuestions = 0;
        while (req.getParameter("question" + numQuestions) != null) {
            numQuestions++;
        }

        Collection<Question> questsQuestions = new ArrayList<>();

        for (int i = 0; i < numQuestions; i++) {
            String questionText = req.getParameter("question" + i);
            ArrayList<Answer> answers = new ArrayList<>();
            answers.add(new Answer(req.getParameter("answerWin" + i), true));
            answers.add(new Answer(req.getParameter("answerLoose" + i), false));
            questsQuestions.add(new Question(questionText, answers));
        }
        if (req.getSession().getAttribute("questsQuestions") != null) {
            List<String> questions = (List<String>) req.getSession().getAttribute("questsQuestions");
        }
        Quest quest = Quest.builder()
                .name(questsName)
                .description(description)
                .questions(questsQuestions)
                .winMessage(winMessage)
                .looseMessage(looseMessage)
                .build();
        questService.create(quest);
        log.info(quest);
        log.info(quest.getId());
        log.info(quest.getAuthorId());
        log.info(quest.getDescription());
        log.info(quest.getQuestions());

        session.setAttribute(Constant.QUEST, quest);



        return Go.CREATE;
    }
}
