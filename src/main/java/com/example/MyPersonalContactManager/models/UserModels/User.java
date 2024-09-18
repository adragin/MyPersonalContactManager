package com.example.MyPersonalContactManager.models.UserModels;

import com.example.MyPersonalContactManager.utils.UtilsUserPassword;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    @Column(name = "user_id")
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

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private UserToken userToken;

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
}
