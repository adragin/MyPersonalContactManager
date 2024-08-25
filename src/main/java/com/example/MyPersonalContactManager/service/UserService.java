package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.models.UserModels.User;

import java.util.List;
import java.util.regex.Pattern;

public class UserService {
    private final List<User> users;

    public UserService(List<User> users) {
        this.users = users;
    }

    public boolean checkCorrectPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasSpecialChar = false;
        boolean hasDigit = false;
        String specialChars = "!@#$%^&*()-+=<>?";

        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            if (specialChars.contains(String.valueOf(currentChar))) {
                hasSpecialChar = true;
            } else if (Character.isDigit(currentChar)) {
                hasDigit = true;
            }
            // Если цифра и спецсимвол есть, то break;
            if (hasDigit && hasSpecialChar) {
                break;
            }
        }
        // Если нет цифры и спец символа:
        if (!hasDigit || !hasSpecialChar) {
            return false;
        }
        return true;
    }

    public boolean checkExistingLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equalsIgnoreCase(login)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCorrectLogin(String login) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(login).matches();
    }
}
