package com.example.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myapplication.Priority

@Entity(tableName = "todo_table")
data class Todo(
    @ColumnInfo(name = "todo_name")
    var name: String,

    @ColumnInfo(name = "todo_description")
    var description: String,

    @ColumnInfo(name = "todo_priority")
    @TypeConverters(Converters::class)
    var priority: Priority
    ) {
    @PrimaryKey(autoGenerate = true)
    var todoId: Long = 0L
}
