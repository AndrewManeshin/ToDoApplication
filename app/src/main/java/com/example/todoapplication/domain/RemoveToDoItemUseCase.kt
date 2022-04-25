package com.example.todoapplication.domain

class RemoveToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    fun removeToDoItem(toDoItemId: Int) {
        toDoListRepository.removeToDoItem(toDoItemId)
    }

}