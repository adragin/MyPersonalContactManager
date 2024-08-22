package com.example.MyPersonalContactManager.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOLogin {
    @NotBlank
    @Email
    private String login;
    @NotBlank
    private String password;

    public UserDTOLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTOLogin{" +
                "login='" + login + '\'' +
                '}';
    }
}
//