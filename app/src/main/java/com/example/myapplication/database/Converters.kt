package com.example.myapplication.database

import androidx.room.TypeConverter
import com.example.myapplication.Priority

class Converters {
    @TypeConverter
    fun intToPriority(value: Int) = enumValues<Priority>()[value]

    @TypeConverter
    fun priorityToInt(value: Priority) = value.ordinal
}