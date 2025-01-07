package com.javarush.repository;

import com.javarush.entity.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class QuestRepository extends AbstractRepo<Quest> {
    @Override
    public Stream<Quest> find(Quest pattern) {
        return map.values()
                .stream()
                .filter(u -> nullOrEquals(pattern.getId(), u.getId()))
                .filter(u -> nullOrEquals(pattern.getDescription(), u.getDescription()))
                .filter(u -> nullOrEquals(pattern.getName(), u.getName()))
                .filter(u -> nullOrEquals(pattern.getAuthorId(), u.getAuthorId()));
    }


    public QuestRepository() {
        String name = "Квест про лису";
        String description = "Квестик про лисичку";
        Long authorId = 1L;

        String winMessage = "Ну молодец";
        String looseMessage = "ты идиот?";


        User user = User.builder()
                .id(5L)
                .login("Liss")
                .password("123")
                .role(Role.ADMIN)
                .build();

        map.put(0L, new Quest(0L, name, description, authorId, addQuestions(), winMessage, looseMessage, user));
    }

    private static ArrayList<Question> addQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        Collection<Answer> answers1 = new ArrayList<>();
        answers1.add(new Answer("в лесу", true, 1L));
        answers1.add(new Answer("не знаю", false, 2L));
        questions.add(new Question("Где лиса?", answers1, 1L));

        Collection<Answer> answers2 = new ArrayList<>();
        answers2.add(new Answer("Сам видел", true, 1L));
        answers2.add(new Answer("а где ей еще быть", false, 2L));
        questions.add(new Question("Откуда знаешь?", answers2, 2L));

        Collection<Answer> answers3 = new ArrayList<>();
        answers3.add(new Answer("кур воровала", true, 1L));
        answers3.add(new Answer("танцевала с петухом", false, 2L));
        questions.add(new Question("И что она делала?", answers3, 3L));

        Collection<Answer> answers4 = new ArrayList<>();
        answers4.add(new Answer("вон от кур одни перья остались", true, 1L));
        answers4.add(new Answer("мамой клянусь", false, 2L));
        questions.add(new Question("чем докажешь?", answers4, 4L));
        return questions;
    }
}
