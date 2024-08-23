package com.example.MyPersonalContactManager.models.UserModels;

import com.example.MyPersonalContactManager.utils.UtilsAuthorization;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class UserToken extends User{
   private final String token;
    private UUID userId;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private UtilsAuthorization authUser = new UtilsAuthorization();

    public UserToken() {
        this.token = authUser.generateToken(getLogin(), getPassword());
        this.userId = getUserId();
        this.createDate = LocalDateTime.now();;
        this.lastUpdateDate = LocalDateTime.now();;
    }
}

