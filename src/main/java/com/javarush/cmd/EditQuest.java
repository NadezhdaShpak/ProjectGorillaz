package com.javarush.cmd;


import com.javarush.entity.Answer;
import com.javarush.entity.Question;
import com.javarush.service.ImageService;
import com.javarush.service.QuestService;
import com.javarush.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class EditQuest implements Command {

    private static final Logger log = LogManager.getLogger(EditQuest.class);
    private final QuestService questService;
    private final ImageService imageService;


    @Override
    public String doGet(HttpServletRequest req) {
        String stringId = req.getParameter(Constant.ID);
        if (stringId != null) {
            long id = Long.parseLong(stringId);
            Optional<com.javarush.entity.Quest> optionalQuest = questService.get(id);
            if (optionalQuest.isPresent()) {
                com.javarush.entity.Quest quest = optionalQuest.get();
                req.setAttribute(Constant.QUEST, quest);
            }
        }
        return getView();
    }

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        com.javarush.entity.Quest currentQuest = (com.javarush.entity.Quest) req.getSession().getAttribute(Constant.QUEST);


        long id = Long.parseLong(req.getParameter(Constant.ID));

        ArrayList<Question> questsQuestions = new ArrayList<>();
        int i = 1;
        while (req.getParameter("questions[" + i + "].text") != null) {
            String question = String.format("questions[%d].text", i);
            String answerWin = String.format("questions[%d].answers[%d].text", i, 1);
            String answerLoose = String.format("questions[%d].answers[%d].text", i, 2);
            String questionText = req.getParameter(question);
            ArrayList<Answer> answers = new ArrayList<>();
            answers.add(new Answer(req.getParameter(answerWin), true, 1L));
            answers.add(new Answer(req.getParameter(answerLoose), false, 2L));
            questsQuestions.add(new Question(questionText, answers, Long.valueOf(i)));
            i++;
        }
        com.javarush.entity.Quest quest = com.javarush.entity.Quest.builder()
                .id(id)
                .name(req.getParameter(Constant.NAME))
                .description(req.getParameter(Constant.DESCRIPTION))
                .questions(questsQuestions)
                .winMessage(req.getParameter(Constant.WIN_MESSAGE))
                .looseMessage(req.getParameter(Constant.LOOSE_MESSAGE))
                .build();
        try {
            imageService.uploadImage(req, quest.getImage());
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
        questService.update(quest);

        return getView() + "?id=" + quest.getId();
    }
}