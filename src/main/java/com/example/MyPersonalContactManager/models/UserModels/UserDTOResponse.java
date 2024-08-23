package com.example.MyPersonalContactManager.models.UserModels;

import com.example.MyPersonalContactManager.utils.UtilsAuthorization;
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

    public UserDTOResponse() {
        this.login = getLogin();
        this.token = getToken();
    }

    @Override
    public String toString() {
        return "UserDTOResponse{" +
                "login='" + login + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
