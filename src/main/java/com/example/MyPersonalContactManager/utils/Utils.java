package com.example.MyPersonalContactManager.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.MyPersonalContactManager.utils.ConstantsContact.DEFAULT_BIRTHDAY;

public class Utils {
    public boolean isDateNull(LocalDate date) {
        if (date == null) {
            return true;
        }
        return false;
    }

    public boolean isDateTimeNull(LocalDateTime dateTime) {
        if (dateTime == null) {
            return true;
        }
        return false;
    }

    public boolean isBirthdayDefault (LocalDate birthday) {
        if (birthday.equals(DEFAULT_BIRTHDAY)) {
            return true;
        }
        return false;
    }
}
