package com.example.MyPersonalContactManager.utils;

import java.time.LocalDate;

import static com.example.MyPersonalContactManager.utils.ConstantsContact.DEFAULT_BIRTHDAY;

public class Utils {
    public boolean isBirthdayNull (LocalDate birthday) {
        if (birthday == null) {
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
