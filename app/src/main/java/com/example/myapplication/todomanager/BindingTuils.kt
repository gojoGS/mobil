package com.example.myapplication.todomanager

import android.graphics.Color
import android.util.TypedValue
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.myapplication.Priority
import com.example.myapplication.R
import com.example.myapplication.database.Todo
import com.google.android.material.color.MaterialColors

@BindingAdapter("todoNameFormatted")
fun TextView.setTodoTitleFormatted(item: Todo) {
    text = String.format("[%s] %s", item.priority, item.name)
    setBackgroundColor(
        when(item.priority) {
            Priority.NONE -> Color.WHITE
            Priority.NORMAL -> Color.GREEN
            Priority.URGENT -> Color.YELLOW
            Priority.CRITICAL -> Color.RED
        }
    )
}
