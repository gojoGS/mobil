package com.example.myapplication.todoviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.TodoDatabaseDao
import com.example.myapplication.todomanager.TodoManagerViewModel

class TodoViewerViewModelFactory(
    private val dataSource: TodoDatabaseDao,
    private val todoId: Long
) : ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewerViewModel::class.java)) {
            return TodoViewerViewModel(dataSource, todoId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}