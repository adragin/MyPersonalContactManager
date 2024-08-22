package com.example.MyPersonalContactManager.model;

import com.example.MyPersonalContactManager.utils.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static com.example.MyPersonalContactManager.utils.ConstantsContact.DEFAULT_BIRTHDAY;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday ;
    private String address;
    private URL photo;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Contact() {
        this.id = String.valueOf(UUID.randomUUID());
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public Contact(String firstName, String lastName, String email, String phone, LocalDate birthday,
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
        this.updateDate = LocalDateTime.now();
    }


    public void setBirthday(LocalDate birthday) {
        Utils utils = new Utils();

        if (utils.isBirthdayNull(birthday)) {
            this.birthday = DEFAULT_BIRTHDAY;
        }
        else this.birthday = birthday;
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
                ", createDate=" + createDate +
                ", updateDate=" + updateDate;
    }
}
