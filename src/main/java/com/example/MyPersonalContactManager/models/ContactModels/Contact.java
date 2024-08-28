package com.example.MyPersonalContactManager.models.ContactModels;

import com.example.MyPersonalContactManager.utils.UtilsContact;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
    private List<Phone> phones;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String address;
    private URL photo;
    private String ownerId;
    private LocalDateTime createDate;
    @Setter
    private LocalDateTime lastUpdateDate;

    public Contact() {
        this.id = String.valueOf(UUID.randomUUID());
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }

    public Contact(String firstName, String lastName, String email, List<String> phones, LocalDate birthday,
                   String address, URL photo) {
        this.id = String.valueOf(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phones = phones;
        this.birthday = birthday;
        this.address = address;
        this.photo = photo;
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }

    //COPY CODE
    public LocalDate getBirthday() {
        UtilsContact utilsContact = new UtilsContact();
        if (utilsContact.isBirthdayDefault(birthday)) {
            return null;
        }
        return this.birthday;
    }

    //COPY CODE
    public void setBirthday(LocalDate birthday) {
        UtilsContact utilsContact = new UtilsContact();

        if (utilsContact.isDateNull(birthday)) {
            this.birthday = DEFAULT_BIRTHDAY;
        } else this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phones + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", photo=" + photo +
                ", createDate=" + createDate +
                ", updateDate=" + lastUpdateDate;
    }
}
