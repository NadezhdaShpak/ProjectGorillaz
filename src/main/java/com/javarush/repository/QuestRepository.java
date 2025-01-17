package com.javarush.repository;

import com.javarush.entity.*;
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
}
