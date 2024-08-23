package com.example.MyPersonalContactManager.models.UserModels;

import com.example.MyPersonalContactManager.utils.UtilsAuthorization;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class User {
    private UUID userId;
    @NotBlank
    private Boolean role;
    @NotBlank @UniqueElements
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String user_name;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    public User() {
        this.userId = UUID.randomUUID();
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }

    public User(Boolean role, String login, String password, String user_name) {
        this.userId = UUID.randomUUID();
        this.role = role;
        this.login = login;
        this.password = password;
        this.user_name = user_name;
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", user_name='" + user_name + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
