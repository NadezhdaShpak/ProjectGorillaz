package com.javarush.entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Answer implements AbstractEntity {
    private String text;
    private boolean win;
    private Long id;

    public Answer(String text, boolean win, Long id) {
        this.id = id;
        this.text = text;
        this.win = win;
    }
}
