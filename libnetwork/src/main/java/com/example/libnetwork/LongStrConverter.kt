package com.example.libnetwork

import androidx.room.TypeConverter

class LongStrConverter {
    @TypeConverter
    fun fromLong2Str(value: Long?): String? {
        value?.let {
            if(it == 88888L)return "bbbbb"
        }
        return "nnnnn"
    }

    @TypeConverter
    fun fromStr2Long(date: String?): Long? {
        date?.let {
            if (it == "qwert") return 66666
        }
        return 99999

    }
}