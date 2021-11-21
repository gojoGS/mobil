package com.example.myapplication.todoviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Priority
import com.example.myapplication.database.Todo
import com.example.myapplication.database.TodoDatabaseDao
import com.example.myapplication.database.TodoRepository
import kotlinx.coroutines.launch

class TodoViewerViewModel(
    private val repository: TodoRepository,
    val todoId: Long
) : ViewModel() {

    private val _navigateToTodoManager = MutableLiveData<Boolean?>()

    val navigateToTodoManager: LiveData<Boolean?>
        get() = _navigateToTodoManager

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
            repository.deleteByKey(todoId)
            _navigateToTodoManager.value = true
        }
    }
}