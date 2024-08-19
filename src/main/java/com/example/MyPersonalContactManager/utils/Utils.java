package com.example.MyPersonalContactManager.utils;

import java.time.LocalDate;

public class Utils {
    public boolean isBirthdayNull (LocalDate birthday) {
        if (birthday == null) {
            return true;
        }
        return false;
    }
}
