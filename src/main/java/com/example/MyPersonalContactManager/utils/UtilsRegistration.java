package com.example.MyPersonalContactManager.utils;

import com.example.MyPersonalContactManager.models.UserModels.User;
import com.example.MyPersonalContactManager.models.UserModels.UserDTORegister;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class UtilsRegistration {
    public String generateToken(String login, String password) {

        Random random = new Random();

        //генерация первых 5 случайных цифр
        String randomFiveDigits = IntStream.range(0, 5)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining());

        return randomFiveDigits + "{" + login + "|" + UtilsUserPassword.hashPassword(password) + "}";
    }

    public boolean checkExistingUser(User user, UserDTORegister userDTORegister) {
        return Objects.nonNull(user) && user.getLogin().equalsIgnoreCase(userDTORegister.getLogin());
    }

    public boolean checkCorrectPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasSpecialChar;
        boolean hasDigit;
        boolean hasUpper;
        String specialChars = "!@#$%^&*()-+=<>?";

        hasSpecialChar = password.chars()
                .mapToObj(c -> (char) c)
                .anyMatch(c -> specialChars.contains(String.valueOf(c)));

        hasDigit = password.chars()
                .anyMatch(Character::isDigit);

        hasUpper = password.chars()
                .anyMatch(Character::isUpperCase);

        // Если нет цифры и спец символа и большой буквы -> false:
        return hasDigit && hasSpecialChar && hasUpper;
    }
}
