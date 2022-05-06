package com.example.todoapplication.domain

class LoadToDoListUseCase(private val toDoListRepository: ToDoListRepository) {

    fun loadToDoList() {
        toDoListRepository.updateToDoList()
    }

}