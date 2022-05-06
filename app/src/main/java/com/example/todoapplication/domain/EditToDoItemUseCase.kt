package com.example.todoapplication.domain

class EditToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    fun editToDoItem(newToDoItem: ToDoItem) {
        toDoListRepository.editToDoItem(newToDoItem)
    }

}