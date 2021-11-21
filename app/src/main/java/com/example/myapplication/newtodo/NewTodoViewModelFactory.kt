package com.example.myapplication.newtodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.TodoDatabaseDao
import com.example.myapplication.database.TodoRepository

class NewTodoViewModelFactory(
    private val repository: TodoRepository) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewTodoViewModel::class.java)) {
            return NewTodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}