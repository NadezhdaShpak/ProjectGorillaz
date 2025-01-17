package com.javarush;

import com.javarush.config.Config;
import com.javarush.config.Winter;
import com.javarush.repository.QuestRepository;
import com.javarush.repository.UserRepository;
import com.javarush.service.GameService;
import com.javarush.service.QuestService;
import com.javarush.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseIT {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected UserRepository userRepository;
    protected QuestRepository questRepository;
    protected UserService userservice;
    protected QuestService questService;
    protected GameService gameService;

    protected BaseIT() {
        Config config = Winter.find(Config.class);
        config.fillEmptyRepository();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        userRepository = Winter.find(UserRepository.class);
        questRepository = Winter.find(QuestRepository.class);
        userservice = Winter.find(UserService.class);
        questService = Winter.find(QuestService.class);
        gameService = Winter.find(GameService.class);
        when(request.getSession()).thenReturn(session);
    }
}