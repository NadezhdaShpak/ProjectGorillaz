package com.javarush.entity;

import java.util.ArrayList;
import java.util.Collection;

public class Question {
    private Collection<Answer> answers;
    private Long id = 1L;
    private String text;
    private Long nextQuestionId = id + 1L;

    public Question(String text, Collection<Answer> answers) {
        this.text = text;
        this.answers = answers;
        id++;

        nextQuestionId++; //???
        }


}
