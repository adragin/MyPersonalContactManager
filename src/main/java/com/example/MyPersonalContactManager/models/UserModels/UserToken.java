package com.example.MyPersonalContactManager.models.UserModels;

import com.example.MyPersonalContactManager.utils.UtilsRegistration;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserToken extends User {
    private final String token;
    private String userId;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private UtilsRegistration authUser = new UtilsRegistration();

    public UserToken() {
        this.token = authUser.generateToken(getLogin(), getPassword());
        this.userId = getUserId();
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }
}

