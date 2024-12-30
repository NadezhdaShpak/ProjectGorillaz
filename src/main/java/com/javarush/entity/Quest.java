package com.javarush.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quest {
    private Long id;
    private String name;
    private String description;
//    private String reward;
    private Long authorId;
    private Collection<Question> questions;
    private String winMessage;
    private String looseMessage;
    private User author;

    public String getImage() { //TODO move to DTO
        if (id < 10) return "quest-0" + id;
        return "quest-" + id;
    }

}
