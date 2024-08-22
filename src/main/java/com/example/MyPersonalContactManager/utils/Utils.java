package com.example.MyPersonalContactManager.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
