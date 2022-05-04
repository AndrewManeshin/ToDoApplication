package com.example.todoapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todoapplication.data.ToDoListRepositoryImpl
import com.example.todoapplication.domain.*

class AddToDoItemViewModel: ViewModel() {

    private val repositoryList = ToDoListRepositoryImpl

    private val addToDoItemUseCase = AddToDoItemUseCase(repositoryList)

    fun addToDoItem(toDoItem: ToDoItem) {
        addToDoItemUseCase.addToDoItem(toDoItem)
    }
}