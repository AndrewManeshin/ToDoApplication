package com.example.todoapplication.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.todoapplication.databinding.FragmentAddTodoItemBinding
import com.example.todoapplication.domain.ToDoItem
import com.example.todoapplication.presentation.viewmodel.AddToDoItemViewModel

class AddToDoItemFragment: Fragment() {

    private lateinit var binding: FragmentAddTodoItemBinding
    private var viewModel = AddToDoItemViewModel()

    private var toDoItem: ToDoItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toDoItem = viewModel.getToDoItem(requireArguments().getInt(ARG_ITEM_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTodoItemBinding.inflate(inflater, container, false)

        binding.etTodoName.setText(toDoItem?.name ?: "")

//        viewModel = AddToDoItemViewModel()

        binding.btAddTodo.setOnClickListener {
            if (binding.etTodoName.text.isNotBlank())
                addToDoItem()
        }
        return binding.root
    }

    private fun addToDoItem() {
        if (toDoItem != null) {
            viewModel.editToDoItem(toDoItem!!.copy(name = binding.etTodoName.text.toString()))
        } else {
            viewModel.addToDoItem(ToDoItem(name = binding.etTodoName.text.toString()))
        }
        navigator().goBack()
    }

    companion object {

        private const val ARG_ITEM_ID = "ARG_ITEM_ID"

        fun newInstance(itemId: Int): AddToDoItemFragment {
            val fragment = AddToDoItemFragment()
            fragment.arguments = bundleOf(ARG_ITEM_ID to itemId)
            return fragment
        }

    }

}