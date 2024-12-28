package com.javarush.cmd;


import com.javarush.entity.Question;
import com.javarush.entity.Role;
import com.javarush.entity.User;
import com.javarush.service.ImageService;
import com.javarush.service.QuestService;
import com.javarush.util.Constant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class Quest implements Command {

    private static final Logger log = LogManager.getLogger(Quest.class);
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
        long id = Long.parseLong(req.getParameter(Constant.ID));
        log.info("id = " + id);
        com.javarush.entity.Quest currentQuest = (com.javarush.entity.Quest) req.getSession().getAttribute(Constant.QUEST);
        User currentUser = (User) req.getSession().getAttribute(Constant.USER);
        com.javarush.entity.Quest quest;
        if (currentUser.getRole() == Role.ADMIN) {
            quest = com.javarush.entity.Quest.builder()
                    .name(req.getParameter(Constant.NAME))
                    .description(req.getParameter(Constant.DESCRIPTION))
                    .winMessage(req.getParameter(Constant.WIN_MESSAGE))
                    .looseMessage(req.getParameter(Constant.LOOSE_MESSAGE))
                    .build();

        }
        else {
            quest = com.javarush.entity.Quest.builder()
                    .name(req.getParameter(Constant.NAME))
                    .description(req.getParameter(Constant.DESCRIPTION))
                    .build();
        }
        return getView() + "?id=" + quest.getId();
    }
}
