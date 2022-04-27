package com.example.todoapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todoapplication.data.ToDoListRepositoryImpl
import com.example.todoapplication.domain.*

class ToDoListViewModel : ViewModel() {

    private val repository = ToDoListRepositoryImpl

    private val getToDoListUseCase = GetToDoListUseCase(repository)
    private val removeToDoItemUseCase = RemoveToDoItemUseCase(repository)
    private val editToDoItemUseCase = EditToDoItemUseCase(repository)

    val todoList = getToDoListUseCase.getToDoList()

    fun removeToDoItem(toDoItemId: Int) {
        removeToDoItemUseCase.removeToDoItem(toDoItemId)
    }

    fun changeEnabledState(toDoItem: ToDoItem) {
        editToDoItemUseCase.editToDoItemUseCase(toDoItem.copy(enabled = !toDoItem.enabled))
    }

}