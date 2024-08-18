package com.example.MyPersonalContactManager.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDTO {
    private String firstName;
    private String phone;

    public ContactDTO(String firstName, String phone) {
        this.firstName = firstName;
        this.phone = phone;
    }

//    public ContactDTO() {
//
//    }
}
