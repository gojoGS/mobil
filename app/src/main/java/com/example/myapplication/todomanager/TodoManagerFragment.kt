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


        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentTodoManagerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_todo_manager, container, false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        val viewModelFactory = TodoManagerViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        val todoManagerViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(TodoManagerViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.todoManagerViewModel = todoManagerViewModel

        val adapter = TodoAdapter(OnTodoClickListener { todo -> todoManagerViewModel.onClick(todo) })
        binding.todoList.adapter = adapter

        todoManagerViewModel.todos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        todoManagerViewModel.navigateToTodo.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Log.d("asd", "navigateToTodo.observe")
                this.findNavController().navigate(TodoManagerFragmentDirections.actionTodoManagerToTodoViewerFragment(todoManagerViewModel.todoId))
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                Log.d("asd", todoManagerViewModel.todoId.toString())

                Log.d("asd", "navigateToTodo.observe after navi")

                todoManagerViewModel.doneNavigating()
            }
        })

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.setLifecycleOwner(this)

        // Add an Observer on the state variable for showing a Snackbar message
        // when the CLEAR button is pressed.


        // Add an Observer on the state variable for Navigating when STOP button is pressed.
        todoManagerViewModel.navigateToNewTodo.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(TodoManagerFragmentDirections.actionTodoManagerToNewTodoFragment())
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                todoManagerViewModel.doneNavigating()
            }
        })

        return binding.root
    }

}