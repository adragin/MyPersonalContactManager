package com.example.MyPersonalContactManager.utils;

import com.example.MyPersonalContactManager.models.UserModels.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UtilsRegistration {
    public String generateToken(String login, String password) {
        return "001{" + login + "|" + password + "}";
    }

    public boolean checkExistingUser(Optional<User> user) {
        if (user.isPresent()) {
            return true;
        }
        return false;
    }
}
