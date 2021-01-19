package com.example.libnetwork

import androidx.room.TypeConverter
import com.example.libnetwork.db.CacheDatabase

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