package com.javarush.repository;

import com.javarush.entity.Question;

import java.util.stream.Stream;

public class QuestionRepository extends AbstractRepo<Question> {


    @Override
    public Stream<Question> find(Question pattern) {
        return map.values()
                .stream()
                .filter(u -> nullOrEquals(pattern.getId(), u.getId()))
                .filter(u -> nullOrEquals(pattern.getId(), u.getId()))
                .filter(u -> nullOrEquals(pattern.getText(), u.getText()));
//                .filter(u -> nullOrEquals(pattern.getGameState(), u.getGameState()));
    }

}