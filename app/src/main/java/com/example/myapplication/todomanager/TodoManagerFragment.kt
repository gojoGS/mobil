package com.example.myapplication.todomanager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.database.TodoDatabase
import com.example.myapplication.database.TodoRepository
import com.example.myapplication.databinding.FragmentTodoManagerBinding
import com.example.myapplication.newtodo.NewTodoFragmentDirections
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 * Use the [TodoManager.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoManagerFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val binding: FragmentTodoManagerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_todo_manager, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        val viewModelFactory = TodoManagerViewModelFactory(TodoRepository(dataSource), application)

        val todoManagerViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(TodoManagerViewModel::class.java)

        binding.todoManagerViewModel = todoManagerViewModel

        val adapter = TodoAdapter(OnTodoClickListener { todo -> todoManagerViewModel.onClick(todo) })
        binding.todoList.adapter = adapter

        todoManagerViewModel.todos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        todoManagerViewModel.navigateToTodo.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Log.d("asd", "navigateToTodo.observe")

                this.findNavController().navigate(TodoManagerFragmentDirections.actionTodoManagerToTodoViewerFragment(todoManagerViewModel.todoId))

                Log.d("asd", todoManagerViewModel.todoId.toString())
                Log.d("asd", "navigateToTodo.observe after navi")

                todoManagerViewModel.doneNavigating()
            }
        })

        binding.setLifecycleOwner(this)

        todoManagerViewModel.navigateToNewTodo.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(TodoManagerFragmentDirections.actionTodoManagerToNewTodoFragment())
                todoManagerViewModel.doneNavigating()
            }
        })

        return binding.root
    }

}