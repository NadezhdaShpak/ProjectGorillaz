package com.javarush.cmd;

import com.javarush.entity.Quest;
import com.javarush.service.QuestService;
import com.javarush.util.Constant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class Home implements Command{
    private final QuestService questService;


    @Override
    public String doGet(HttpServletRequest req) {
        Collection<Quest> quests = questService.getAll();
        req.setAttribute(Constant.QUESTS, quests);
        return getView();
    }
}
