package com.example.myapplication.newtodo

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_new_todo.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Priority
import com.example.myapplication.R
import com.example.myapplication.database.TodoDatabase
import com.example.myapplication.database.TodoRepository
import com.example.myapplication.databinding.FragmentNewTodoBinding
import com.example.myapplication.todomanager.TodoAdapter

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class NewTodoFragment : Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewTodoBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_todo,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        val viewModelFactory = NewTodoViewModelFactory(TodoRepository(dataSource))

        val newTodoViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(NewTodoViewModel::class.java)

        binding.newTodoViewModel = newTodoViewModel

        newTodoViewModel.navigateToTodoManager.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(NewTodoFragmentDirections.actionNewTodoFragmentToTodoManager())
                newTodoViewModel.doneNavigating()
            }
        })

        binding.priority.adapter =
            context?.let { ArrayAdapter(it,  android.R.layout.simple_list_item_1, Priority.values()) }

        binding.okButton.setOnClickListener {
            val name = binding.todoName.text.toString()
            val description = binding.todoDescription.text.toString()
            val priority = binding.priority.selectedItem as Priority

            newTodoViewModel.onOk(name, description, priority)
        }

        return binding.root
    }
}