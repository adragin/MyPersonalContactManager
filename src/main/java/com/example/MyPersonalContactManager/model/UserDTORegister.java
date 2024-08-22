package com.example.MyPersonalContactManager.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDTORegister {
    @NotBlank
    @Email
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String name;

    public UserDTORegister(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

//    public UserDTORegister() {
//    }

    @Override
    public String toString() {
        return "UserDTORegister{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
//