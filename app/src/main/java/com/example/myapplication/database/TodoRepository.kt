package com.example.myapplication.database

import androidx.lifecycle.LiveData

class TodoRepository (private val todoDatabaseDao: TodoDatabaseDao) {
    suspend fun insert(todo: Todo) = todoDatabaseDao.insert(todo)

    suspend fun deleteByKey(key: Long) = todoDatabaseDao.deleteTodo(key)

    suspend fun clear() = todoDatabaseDao.delete()

    fun getAllTodo(): LiveData<List<Todo>> = todoDatabaseDao.getAllTodo()

    fun getTodoByKey(key: Long): Todo = todoDatabaseDao.get(key)
}