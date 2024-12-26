package com.javarush.config;

import com.javarush.entity.*;
import com.javarush.service.ImageService;
import com.javarush.service.QuestService;
import com.javarush.service.UserService;
import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class Config {

    private final UserService userService;
    private final QuestService questService;
    private final ImageService imageService;

    public void fillEmptyRepository() {
        if (userService.get(1L).isEmpty()) {
            User admin = biuldUser("Carl", "admin", Role.ADMIN);
            userService.create(admin);
            User alisa = biuldUser("Alisa", "qwerty", Role.USER);
            userService.create(alisa);
            User bob = biuldUser("Bob", "123", Role.GUEST);
            userService.create(bob);

            addDemoQuests(admin);
        }
    }

    private static User biuldUser(String name, String password, Role role) {
        return User.builder()
                .login(name)
                .password(password)
                .role(role)
                .build();
    }

    private void addDemoQuests(User author) {
        Long authorId = author.getId();
        String name = "Test quest";
        String description = "Test description";
        Collection<Question> questions = new ArrayList<>();
        Collection<Answer> answers = new ArrayList<>();

        Quest quest = Quest.builder()
                .id(authorId)
                .image("no-image.webp")
                .description("Играем в неопознанный летающий объект")
                .looseMessage("Вы проиграли")
                .winMessage("Вы выиграли")
                .questions(questions)
                .build();

        answers.add(new Answer("Принять вызов", true));
        answers.add(new Answer("Отклонить вызов", false));
        questions.add(new Question("Ты потерял память. Принять вызов НЛО?", answers));
        answers.clear();
        answers.add(new Answer("Отказаться подниматься на мостик", false));
        answers.add(new Answer("Подняться на мостик", true));
        questions.add(new Question("Ты принял вызов. Подняться на мостик к капитану?", answers));
        answers.clear();
        answers.add(new Answer("Солгать о себе", false));
        answers.add(new Answer("Рассказать правду", true));
        questions.add(new Question("Ты поднялся на мостик. Ты кто?", answers));
        quest.setQuestions(questions);
        questService.create(quest);

    }
}
