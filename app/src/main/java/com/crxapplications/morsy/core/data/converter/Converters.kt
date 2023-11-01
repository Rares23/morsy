package com.crxapplications.morsy.core.data.converter

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let {
        Date(it)
    }

    @TypeConverter
    fun fromDateToTimestamp(date: Date?): Long? = date?.time
}