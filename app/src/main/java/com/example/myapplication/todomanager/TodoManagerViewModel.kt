package com.example.myapplication.todomanager

import android.app.Application
import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.myapplication.Priority
import com.example.myapplication.database.Todo
import com.example.myapplication.database.TodoDatabaseDao
import com.example.myapplication.database.TodoRepository
import kotlinx.coroutines.launch

class TodoManagerViewModel(
    private val repository: TodoRepository,
    application: Application
) : ViewModel() {

    val todos = repository.getAllTodo()
    val muberOfTodos: LiveData<Int> = Transformations.map(todos) { data -> data.size }

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
            repository.clear()
        }
    }


    fun onClick(todo: Todo) {
        Log.d("asd", todo.todoId.toString() + todo.toString())
        todoId = todo.todoId
        _navigateToTodo.value = true
    }


}