package com.example.MyPersonalContactManager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ContactDTOBig {
    @NotBlank
    private String firstName;
    private String lastName;
    private String email;
    @NotBlank
    private String phone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday ;
    private String address;
    private URL photo;

    public ContactDTOBig(String firstName, String lastName, String email, String phone, LocalDate birthday, String address, URL photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.photo = photo;
    }

    public ContactDTOBig() {
    }
}
