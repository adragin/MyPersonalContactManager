package com.example.MyPersonalContactManager.models.UserModels;

import com.example.MyPersonalContactManager.models.Error;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOResponse {
    private Error error;
    @NotBlank
    @Email
    private String login;
    private String token;
}
