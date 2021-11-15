package com.example.myapplication.todomanager

import android.app.Application
import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.myapplication.Priority
import com.example.myapplication.database.Todo
import com.example.myapplication.database.TodoDatabaseDao
import kotlinx.coroutines.launch

class TodoManagerViewModel(
    dataSource: TodoDatabaseDao,
    application: Application
) : ViewModel() {
    val database = dataSource

    val todos = database.getAllTodo()

    private val _navigateToNewTodo = MutableLiveData<Boolean?>()
    private val _navigateToTodo = MutableLiveData<Boolean>()
    var todoId: Long = 0
    get

    val navigateToNewTodo: LiveData<Boolean?>
        get() = _navigateToNewTodo

    val navigateToTodo: LiveData<Boolean?>
        get() = _navigateToTodo

    fun doneNavigating() {
        _navigateToNewTodo.value = null
        _navigateToTodo.value = null
    }

    fun onNew() {
        _navigateToNewTodo.value = true
    }

    fun onClear() {
        viewModelScope.launch {
            database.delete()
        }
    }


    fun onClick(todo: Todo) {
        Log.d("asd", todo.todoId.toString() + todo.toString())
        todoId = todo.todoId
        _navigateToTodo.value = true
    }


}