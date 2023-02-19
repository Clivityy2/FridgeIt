package com.example.fridgeit2.data

import androidx.room.TypeConverter
import java.time.LocalDate

class DateTypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate?{
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun toTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}