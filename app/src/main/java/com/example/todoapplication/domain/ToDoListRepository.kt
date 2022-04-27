package com.example.todoapplication.domain

import androidx.lifecycle.LiveData

interface ToDoListRepository {

    fun addToDoItem(toDoItem: ToDoItem)

    fun editToDoItem(newToDoItem: ToDoItem)

    fun getToDoItem(toDoItemId: Int): ToDoItem

    fun getToDoList(): LiveData<List<ToDoItem>>

    fun removeToDoItem(toDoItemId: Int)

    fun moveToDoItem(toDoItem: ToDoItem, moveBy: Int)

}