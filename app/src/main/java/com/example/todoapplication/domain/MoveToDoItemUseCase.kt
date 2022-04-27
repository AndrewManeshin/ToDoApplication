package com.example.todoapplication.domain

class MoveToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    fun moveToDoItem(toDoItem: ToDoItem, moveBy: Int) {
        toDoListRepository.moveToDoItem(toDoItem, moveBy)
    }

}