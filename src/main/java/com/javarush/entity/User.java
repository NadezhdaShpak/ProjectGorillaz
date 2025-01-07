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
public class User implements AbstractEntity {

    private Long id;
    private String login;
    private String password;
    private Role role;

    private final Collection<Game> games = new ArrayList<>();

    public String getImage() { //TODO move to DTO
        if (id < 10) return "user-0" + id;
        return "user-" + id;
    }

}
