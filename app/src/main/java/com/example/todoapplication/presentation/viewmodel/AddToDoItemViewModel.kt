package com.example.todoapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todoapplication.data.ToDoListRepositoryImpl
import com.example.todoapplication.domain.AddToDoItemUseCase
import com.example.todoapplication.domain.ToDoItem

class AddToDoItemViewModel: ViewModel() {

    private val repository = ToDoListRepositoryImpl

    private val addToDoItemUseCase = AddToDoItemUseCase(repository)

    fun addToDoItem(toDoItem: ToDoItem) {
        addToDoItemUseCase.addToDoItem(toDoItem)
    }

}