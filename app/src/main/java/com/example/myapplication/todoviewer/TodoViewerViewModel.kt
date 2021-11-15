package com.example.myapplication.todoviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Priority
import com.example.myapplication.database.Todo
import com.example.myapplication.database.TodoDatabaseDao
import kotlinx.coroutines.launch

class TodoViewerViewModel(
    dataSource: TodoDatabaseDao,
    val todoId: Long
) : ViewModel() {
    val database = dataSource

    private val _navigateToTodoManager = MutableLiveData<Boolean?>()

    val navigateToTodoManager: LiveData<Boolean?>
        get() = _navigateToTodoManager

    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToTodoManager.value = null
    }

    fun onCancel() {
        viewModelScope.launch {
            _navigateToTodoManager.value = true
        }
    }

    fun onDone() {
        viewModelScope.launch {
            database.deleteTodo(todoId)
            _navigateToTodoManager.value = true
        }
    }
}