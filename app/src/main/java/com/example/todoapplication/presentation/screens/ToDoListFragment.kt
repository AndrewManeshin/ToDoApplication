package com.example.todoapplication.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapplication.databinding.FragmentTodoListBinding
import com.example.todoapplication.domain.ToDoItem
import com.example.todoapplication.presentation.ToDoActionListener
import com.example.todoapplication.presentation.ToDoListAdapter
import com.example.todoapplication.presentation.viewmodel.ToDoListViewModel

class ToDoListFragment : Fragment() {

    private lateinit var binding: FragmentTodoListBinding
    private lateinit var viewModel: ToDoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)

        viewModel = ToDoListViewModel()
        viewModel.loadToDoList()

        binding.rvTodoList.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ToDoListAdapter(object: ToDoActionListener {
            override fun onChangeEnabledState(toDoItem: ToDoItem) {
                viewModel.changeEnabledState(toDoItem)
            }

            override fun onToDoDelete(toDoItemId: Int) {
                viewModel.removeToDoItem(toDoItemId)
            }

            override fun onToDoMove(toDoItem: ToDoItem, moveBy: Int) {
                viewModel.moveToDoItem(toDoItem, moveBy)
            }
        })

        viewModel.toDoList.observe(viewLifecycleOwner) {
            adapter.toDoItems = it
        }

        binding.rvTodoList.adapter = adapter

        binding.addButton.setOnClickListener {
            navigator().addItem()
        }
        return binding.root
    }

}