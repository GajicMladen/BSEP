package com.example.SmartHouseAPI.util;

import java.time.LocalDateTime;
import java.util.Date;

public class DateConverter {

    public static Date localDateTimeToDate(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }
}
