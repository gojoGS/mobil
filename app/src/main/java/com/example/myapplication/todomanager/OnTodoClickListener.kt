package com.example.myapplication.todomanager

import com.example.myapplication.database.Todo

class OnTodoClickListener(val clickListener: (todo: Todo) -> Unit) {
    fun onClick(todo: Todo) = clickListener(todo)
}
