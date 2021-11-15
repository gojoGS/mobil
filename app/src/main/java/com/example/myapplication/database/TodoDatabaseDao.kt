package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters

@Dao
interface TodoDatabaseDao {
    @Insert
    fun insert(todo: Todo): Unit

    @Query("DELETE FROM todo_table WHERE todoId = :key")
    fun deleteTodo(key: Long): Unit

    @Query("DELETE FROM todo_table")
    fun delete()

    @Query("SELECT * FROM todo_table ORDER BY todo_priority DESC")
    fun getAllTodo(): LiveData<List<Todo>>

    @Query("SELECT * from todo_table WHERE todoId = :key")
    fun get(key: Long): Todo
}