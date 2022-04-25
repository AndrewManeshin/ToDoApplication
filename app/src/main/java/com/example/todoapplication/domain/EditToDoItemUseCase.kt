package com.example.todoapplication.domain

class EditToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    fun editToDoItemUseCase(newToDoItem: ToDoItem) {
        toDoListRepository.egitToDoItem(newToDoItem)
    }

}