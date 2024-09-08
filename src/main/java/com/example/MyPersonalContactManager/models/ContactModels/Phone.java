package com.example.MyPersonalContactManager.models.ContactModels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contacts_phones")
public class Phone {
    @Column(name = "contact_id")
    private String contactId;
    @Id
    private String phoneNumber;

    public Phone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
