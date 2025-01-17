package com.javarush.repository;

import com.javarush.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class QuestRepositoryTest {
    private final QuestRepository questRepository = new QuestRepository();
    private Quest testQuest;

    @BeforeEach
    void createTestQuest() {
        ArrayList<Question> questions = new ArrayList<>();
        Collection<Answer> answers = new ArrayList<>();
        answers.add(new Answer("testWinAnswer", true, 1L));
        answers.add(new Answer("testLooseAnswer", false, 2L));
        questions.add(new Question("testQuestion", answers, 1L));
        testQuest = Quest.builder()
                .name("Test name")
                .authorId(5L)
                .description("Test description")
                .winMessage("Test win message")
                .looseMessage("Test loose message")
                .questions(questions)
                .build();
        questRepository.create(testQuest);
        assertTrue(questRepository.getAll().contains(testQuest));
    }

    @Test
    void get() {
        Quest quest = questRepository.get(testQuest.getId());
        Assertions.assertEquals(testQuest, quest);
    }


    @Test
    void update() {
        testQuest.setName("newName");
        questRepository.update(testQuest);
        assertEquals("newName", testQuest.getName());
    }

    @Test
    void delete() {
        questRepository.delete(testQuest);
        assertFalse(questRepository.getAll().contains(testQuest));

    }

    @Test
    void find() {
        Quest pattern = Quest.builder().name("Test name").build();
        Stream<Quest> questStream = questRepository.find(pattern);
        Assertions.assertEquals(testQuest, questStream.findFirst().orElseThrow());
    }


}