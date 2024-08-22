package com.example.MyPersonalContactManager.models.ContactModels;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDTOShort {
    @NotBlank
    private String firstName;
    @NotBlank
    private String phone;

    public ContactDTOShort(String firstName, String phone) {
        this.firstName = firstName;
        this.phone = phone;
    }

}
