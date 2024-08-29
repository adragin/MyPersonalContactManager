package com.example.MyPersonalContactManager.models.ContactModels;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Phone {
    private String id;
    private String phoneNumber;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    public Phone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }

    public Phone() {
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }
}
