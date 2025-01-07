package com.javarush.service;

import com.javarush.entity.Quest;
import com.javarush.repository.QuestRepository;

import java.util.Collection;
import java.util.Optional;

public class QuestService {
    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public void create(Quest quest) {
        questRepository.create(quest);
    }

    public void update(Quest quest) {
        questRepository.update(quest);
    }

    public void delete(Quest quest) {
        questRepository.delete(quest);
    }

    public Collection<Quest> getAll() {
        return questRepository.getAll();
    }

    public Optional<Quest> get(long id) {
        return Optional.ofNullable(questRepository.get(id));
    }
}
