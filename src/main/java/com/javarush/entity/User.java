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
    private Long numberOfLooseGames;
    private Long numberOfWinGames;


    public User(Long id, String login, String password, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.numberOfWinGames = 0L;
        this.numberOfLooseGames = 0L;
    }

    private final Collection<Game> games = new ArrayList<>();

    public String getImage() { //TODO move to DTO
        if (id < 10) return "user-0" + id;
        return "user-" + id;
    }

}
