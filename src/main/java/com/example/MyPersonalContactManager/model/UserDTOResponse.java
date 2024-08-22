package com.example.MyPersonalContactManager.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDTOResponse {
    @NotBlank
    @Email
    private String login;
    private String token;

    public UserDTOResponse(String login, String token) {
        this.login = login;
        this.token = generateToken();
    }
    private String  generateToken(){
        return UUID.randomUUID().toString().replace("-","");
    }

    @Override
    public String toString() {
        return "UserDTOResponse{" +
                "login='" + login + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
