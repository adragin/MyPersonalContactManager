package com.example.MyPersonalContactManager.utils;

import com.example.MyPersonalContactManager.models.UserModels.User;
import com.example.MyPersonalContactManager.models.UserModels.UserDTORegister;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UtilsRegistration {
    public String generateToken(String login, String password) {

        return "001{" + login + "|" + password + "}";
    }

    public boolean checkExistingUser(User user, UserDTORegister userDTORegister) {
        if (Objects.nonNull(user) && user.getLogin().
                equalsIgnoreCase(userDTORegister.getLogin())) {
            return true;
        }
        return false;
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


}
