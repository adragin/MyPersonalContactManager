package com.example.MyPersonalContactManager.models.UserModels;

import com.example.MyPersonalContactManager.models.Error;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
//@AllArgsConstructor
public class UserDTOResponse {
    private Error error;
    @NotBlank
    @Email
    private String login;
    private String token;

    public UserDTOResponse(String login, String token) {
        this.login = login;
        this.token = token;
    }

    public UserDTOResponse(Error error) {
        this.error = error;
    }

    public UserDTOResponse(String token) {
        this.token = token;
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
