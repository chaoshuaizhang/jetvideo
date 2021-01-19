package com.example.libnetwork

import androidx.room.TypeConverter

class LongStrConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): String? {
        return value?.let { it.toString() }
    }

    @TypeConverter
    fun dateToTimestamp(date: String?): Long? {
        return date?.toLong()
    }
}