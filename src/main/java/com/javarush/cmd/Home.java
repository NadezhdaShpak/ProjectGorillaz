package com.javarush.cmd;

import com.javarush.entity.Quest;
import com.javarush.entity.User;
import com.javarush.service.ImageService;
import com.javarush.service.QuestService;
import com.javarush.service.UserService;
import com.javarush.util.Constant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class Home implements Command{
    private final QuestService questService;
    private final ImageService imageService;


    @Override
    public String doGet(HttpServletRequest req) {
        Collection<Quest> quests = questService.getAll();
        req.setAttribute(Constant.QUESTS, quests);
        return getView();
    }
}
