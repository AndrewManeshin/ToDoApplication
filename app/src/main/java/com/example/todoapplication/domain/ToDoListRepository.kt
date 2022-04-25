package com.example.todoapplication.domain

import androidx.lifecycle.LiveData

interface ToDoListRepository {

    fun addToDoItem(toDoItem: ToDoItem)

    fun egitToDoItem(newToDoItem: ToDoItem)

    fun getToDOItem(toDoItemId: Int): ToDoItem

    fun getToDoList(): LiveData<List<ToDoItem>>

    fun removeToDoItem(toDoItemId: Int)

}