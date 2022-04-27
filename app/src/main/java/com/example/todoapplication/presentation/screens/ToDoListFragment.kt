package com.example.todoapplication.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    ): View? {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[ToDoListViewModel::class.java]

        binding.rvTodoList.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ToDoListAdapter(object: ToDoActionListener {
            override fun changeEnabledState(toDoItem: ToDoItem) {
                viewModel.changeEnabledState(toDoItem)
            }
        })

        viewModel.todoList.observe(viewLifecycleOwner) {
            adapter.toDoItems = it
        }

        binding.rvTodoList.adapter = adapter

        binding.addButton.setOnClickListener {
            navigator().addItem()
        }
        return binding.root
    }

}