package com.example.myapplication.todomanager

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.TodoDatabaseDao
import com.example.myapplication.database.TodoRepository

class TodoManagerViewModelFactory(
    private val repository: TodoRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoManagerViewModel::class.java)) {
            return TodoManagerViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}