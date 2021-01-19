package com.example.libnetwork.db;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static String toDate(Long timestamp) {
        return timestamp == null ? null : Long.toString(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(String date) {
        return date == null ? null : Long.parseLong(date);
    }
}
