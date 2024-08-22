package com.example.MyPersonalContactManager.models.UserModels;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class User {
    private UUID uuid;
    private Boolean role;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    private String token;
    @NotBlank
    private String name;

    public User(UUID uuid, String token) {
        this.uuid = UUID.randomUUID();
        this.token = generateToken();
    }

    public User(UUID uuid, Boolean role, String login, String password, String token, String name) {
        this.uuid = UUID.randomUUID();
        this.role = role;
        this.login = login;
        this.password = password;
        this.token = generateToken();
        this.name = name;
    }

    // Пока так
    private String generateToken(){

        return UUID.randomUUID().toString().replace("-","");
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
//