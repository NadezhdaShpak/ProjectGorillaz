package com.javarush.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    private Collection<Answer> answers;
    private String text;
    private Long id;


    public Question(String text, Collection<Answer> answers, Long id) {
        this.text = text;
        this.answers = answers;
        this.id = id;
        }


}
