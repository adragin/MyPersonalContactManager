package com.example.MyPersonalContactManager.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Contact {
    private String id;
    @NotBlank
    private String firstName;
    private String lastName;
    private String email;
    @NotBlank
    private String phone;
    private Date birthday;
    private String address;
    private URL photo;
    private LocalDateTime createDate;

//    public Contact() {
//    }

    public Contact(String firstName, String lastName, String email, String phone, Date birthday,
                   String address, URL photo) {
        this.id = String.valueOf(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.photo = photo;
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", photo=" + photo +
                ", createDate=" + createDate;
    }
}
