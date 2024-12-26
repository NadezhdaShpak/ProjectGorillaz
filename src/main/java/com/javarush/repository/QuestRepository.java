package com.javarush.repository;

import com.javarush.entity.Quest;
import com.javarush.entity.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class QuestRepository implements Repository<Quest> {
    private final Map<Long, Quest> map = new HashMap<>();

    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());

    @Override
    public Collection<Quest> getAll() {
        return map.values();
    }

    @Override
    public Optional<Quest> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void create (Quest entity) {
            entity.setId(id.incrementAndGet());
            update(entity);
    }

    @Override
    public void update(Quest entity) {
        map.put(entity.getId(), entity);
    }

    @Override
    public void delete(Quest entity) {
        map.remove(entity.getId());
    }
}
