package com.example.myapplication.newtodo

import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Priority
import com.example.myapplication.R
import com.example.myapplication.database.Todo
import com.example.myapplication.database.TodoDatabaseDao
import com.example.myapplication.database.TodoRepository
import com.example.myapplication.databinding.FragmentNewTodoBinding
import kotlinx.coroutines.launch

class NewTodoViewModel (
    private val repository: TodoRepository
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

    fun onOk(name: String, description: String, priority: Priority) {
        if (name.length.equals(0))
            return

        if (description.length.equals(0))
            return

        viewModelScope.launch {
            repository.insert(Todo(name, description, priority))
            _navigateToTodoManager.value = true
        }
    }
}