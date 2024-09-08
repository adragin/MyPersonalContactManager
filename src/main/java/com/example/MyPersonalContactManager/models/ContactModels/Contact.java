package com.example.MyPersonalContactManager.models.ContactModels;

import com.example.MyPersonalContactManager.models.UserModels.User;
import com.example.MyPersonalContactManager.utils.UtilsContact;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.MyPersonalContactManager.utils.ConstantsContact.DEFAULT_BIRTHDAY;
import static com.example.MyPersonalContactManager.utils.ConstantsContact.DEFAULT_PHOTO_URL;

@Getter
@Setter
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @NotBlank
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany
    @JoinColumn(name = "contact_id")
    private List<Phone> phones;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String address;
    private URL photo;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private User owner;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    public Contact() {
        this.id = String.valueOf(UUID.randomUUID());
        try {
            this.photo = new URL(DEFAULT_PHOTO_URL);
        } catch (MalformedURLException e) {
            this.photo = null;
        }
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }

    public LocalDate getBirthday() {
        UtilsContact utilsContact = new UtilsContact();
        if (utilsContact.isBirthdayDefault(birthday)) {
            return null;
        }
        return this.birthday;
    }

    public void setBirthday(LocalDate birthday) {
        UtilsContact utilsContact = new UtilsContact();
        if (utilsContact.isDateNull(birthday))
            this.birthday = DEFAULT_BIRTHDAY;
        else
            this.birthday = birthday;
    }
}
