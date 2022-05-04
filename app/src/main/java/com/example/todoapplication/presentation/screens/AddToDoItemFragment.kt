package com.example.todoapplication.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapplication.databinding.FragmentAddTodoItemBinding
import com.example.todoapplication.domain.ToDoItem
import com.example.todoapplication.presentation.viewmodel.AddToDoItemViewModel

class AddToDoItemFragment: Fragment() {

    private lateinit var binding: FragmentAddTodoItemBinding
    private lateinit var viewModel: AddToDoItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTodoItemBinding.inflate(inflater, container, false)
        viewModel = AddToDoItemViewModel()

        binding.btAddTodo.setOnClickListener {
            if (binding.etTodoName.text.isNotBlank())
                addToDoItem()
        }

        return binding.root
    }

    private fun addToDoItem() {
        viewModel.addToDoItem(ToDoItem(name = binding.etTodoName.text.toString()))
        navigator().goBack()
    }

}