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

import static com.example.MyPersonalContactManager.utils.ConstantsContact.DEFAULT_BIRTHDAY;

@Getter
@Setter
public class ContactDTOBig {
    @NotBlank
    private String firstName;
    private String lastName;
    private String email;
    @NotBlank
    private List<String> phones;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String address;
    private URL photo;
    private LocalDateTime lastUpdateDate;

    public ContactDTOBig(String firstName, String lastName, String email, List<String> phones, LocalDate birthday, String address, URL photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phones = phones;
        this.birthday = birthday;
        this.address = address;
        this.photo = photo;
        this.lastUpdateDate = LocalDateTime.now();
    }

    public ContactDTOBig() {
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
}
