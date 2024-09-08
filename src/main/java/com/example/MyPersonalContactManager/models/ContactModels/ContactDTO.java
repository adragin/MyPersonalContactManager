package com.example.MyPersonalContactManager.models.ContactModels;

import com.example.MyPersonalContactManager.utils.UtilsContact;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.MyPersonalContactManager.utils.ConstantsContact.DEFAULT_BIRTHDAY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> phones;  // Список строк вместо списка объектов Phone
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String address;
    private URL photo;
    private String ownerId;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    public LocalDate getBirthday() {
        UtilsContact utilsContact = new UtilsContact();
        if (utilsContact.isBirthdayDefault(birthday)) {
            return null;
        }
        return this.birthday;
    }

    public void setBirthday(LocalDate birthday) {
        UtilsContact utilsContact = new UtilsContact();
        if (utilsContact.isDateNull(birthday)) {
            this.birthday = DEFAULT_BIRTHDAY;
        } else this.birthday = birthday;
    }
}
