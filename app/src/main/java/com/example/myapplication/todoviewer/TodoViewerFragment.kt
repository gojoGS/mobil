package com.example.myapplication.todoviewer

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
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.database.TodoDatabase
import com.example.myapplication.databinding.FragmentTodoViewerBinding
import com.example.myapplication.todomanager.TodoManagerFragmentDirections

class TodoViewerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTodoViewerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_todo_viewer,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        Log.d("sad", "before args call")
        val viewModelFactory = TodoViewerViewModelFactory(dataSource, TodoViewerFragmentArgs.fromBundle(requireArguments()).todoId)
        Log.d("sad", "after args call")


        val todoViewerViewModel =
            ViewModelProvider(this, viewModelFactory).get(TodoViewerViewModel::class.java)

        binding.todoViewerViewModel = todoViewerViewModel
        binding.todo = dataSource.get(TodoViewerFragmentArgs.fromBundle(requireArguments()).todoId)

        todoViewerViewModel.navigateToTodoManager.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(TodoViewerFragmentDirections.actionTodoViewerFragmentToTodoManager())
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                todoViewerViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}