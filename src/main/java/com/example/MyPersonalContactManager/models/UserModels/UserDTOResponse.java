package com.example.MyPersonalContactManager.models.UserModels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
//@AllArgsConstructor
public class UserDTOResponse {
    private String errorMessage;
    @NotBlank
    @Email
    private String login;
    private String token;

    public UserDTOResponse(String login, String token) {
        this.login = login;
        this.token = token;
    }

    public UserDTOResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    //
//    @Override
//    public String toString() {
//        return "UserDTOResponse{" +
//                "login='" + login + '\'' +
//                ", token='" + token + '\'' +
//                '}';
//    }
}
