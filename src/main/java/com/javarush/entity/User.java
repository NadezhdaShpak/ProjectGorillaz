package com.javarush.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    private String login;

    private String password;

    private Role role;

    public String getImage() { //TODO move to DTO
        if (id < 10) return "user-0" + id;
        return "user-" + id;
    }

}
