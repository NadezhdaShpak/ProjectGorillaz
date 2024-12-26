package com.javarush.controller;

import com.javarush.cmd.Command;
import com.javarush.config.Winter;
import com.javarush.util.Go;
import jakarta.servlet.http.HttpServletRequest;

public class HttpResolver {

    public Command resolve(HttpServletRequest request) {
        //   /cmd-example
        try {
            String requestURI = request.getRequestURI();
            requestURI = requestURI.equals("/") ? Go.HOME : requestURI;
            String kebabName = requestURI.split("[?#/]")[1];
            String simpleName = convertKebabStyleToCamelCase(kebabName);
            String fullName = Command.class.getPackageName() + "." + simpleName;
            Class<?> aClass = Class.forName(fullName);
            return (Command) Winter.find(aClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertKebabStyleToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : input.toCharArray()) {
            if (c == '-') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        return result.toString();
    }
}
