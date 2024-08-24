package com.example.MyPersonalContactManager.models.UserModels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTOResponse {
    @NotBlank
    @Email
    private String login;
    private String token;

//    public UserDTOResponse() {
//        this.login = getLogin();
//        this.token = getToken();
//    }
//
//    @Override
//    public String toString() {
//        return "UserDTOResponse{" +
//                "login='" + login + '\'' +
//                ", token='" + token + '\'' +
//                '}';
//    }
}
