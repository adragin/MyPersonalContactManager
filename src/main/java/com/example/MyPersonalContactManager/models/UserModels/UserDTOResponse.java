package com.example.MyPersonalContactManager.models.UserModels;

import com.example.MyPersonalContactManager.models.Error;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTOResponse {
    private Error error;
    @NotBlank
    @Email
    private String login;
    private String token;
}
