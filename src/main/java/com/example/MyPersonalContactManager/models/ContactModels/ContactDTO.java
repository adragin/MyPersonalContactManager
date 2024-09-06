package com.example.MyPersonalContactManager.models.ContactModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDate birthday;
    private String address;
    private URL photo;
    private String ownerId;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}
