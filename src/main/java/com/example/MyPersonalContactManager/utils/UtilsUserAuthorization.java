package com.example.MyPersonalContactManager.utils;

import com.example.MyPersonalContactManager.models.UserModels.User;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOLogin;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UtilsUserAuthorization {
    public boolean checkExistingUser(Optional<User> existingUser, UserDTOLogin userLogin) {

        if (existingUser.get().getPassword().equals(userLogin.getPassword()) &&
                (existingUser.get().getLogin().equals(userLogin.getLogin()))) {
            return true;
        }
        return false;
    }
}
