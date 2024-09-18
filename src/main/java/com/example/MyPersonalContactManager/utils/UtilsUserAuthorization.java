package com.example.MyPersonalContactManager.utils;

import com.example.MyPersonalContactManager.models.UserModels.User;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOLogin;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UtilsUserAuthorization {
    public boolean checkExistingUser(User existingUser, UserDTOLogin userLogin) {
        if (!Objects.nonNull(existingUser)) {
            return false;
        }

        if (existingUser.getPassword().equals(UtilsUserPassword.hashPassword(userLogin.getPassword())) &&
                (existingUser.getLogin().equals(userLogin.getLogin()))) {

            return true;
        }
        return false;
    }
}
