package com.example.MyPersonalContactManager.utils;

import org.springframework.stereotype.Component;

@Component
public class UtilsAuthorization {
    public String generateToken(String login, String password) {
        return "001{" + login + "|" + password + "}";
    }
}
