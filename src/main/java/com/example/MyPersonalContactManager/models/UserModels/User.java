package com.example.MyPersonalContactManager.models.UserModels;

import com.example.MyPersonalContactManager.utils.UtilsUserPassword;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;
    @NotBlank
    private Boolean role; // true->admin, false->user
    @NotBlank
    @UniqueElements
    private String login; // email
    @NotBlank
    private String password;
    @NotBlank
    private String userName; // FullName or FirstName
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    public User() {
        this.userId = String.valueOf(UUID.randomUUID());
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }

    public User(Boolean role, String login, String password, String userName) {
        this.userId = String.valueOf(UUID.randomUUID());
        this.role = role;
        this.login = login;
        this.password = UtilsUserPassword.hashPassword(password);
        this.userName = userName;
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
                ", user_name='" + userName + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
