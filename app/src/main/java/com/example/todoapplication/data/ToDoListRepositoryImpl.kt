package com.example.todoapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapplication.domain.ToDoItem
import com.example.todoapplication.domain.ToDoListRepository
import java.lang.RuntimeException

object ToDoListRepositoryImpl : ToDoListRepository {

    private val todoListLD = MutableLiveData<List<ToDoItem>>()
    private val todoList = mutableListOf<ToDoItem>()

    init {
        for (i in 0..10) {
            val item = ToDoItem(
                id = i,
                name = "Name $i",
                enabled = true
            )
            addToDoItem(item)
        }
    }

    override fun addToDoItem(toDoItem: ToDoItem) {
        if (toDoItem.id == ToDoItem.UNDEFINED_ID || todoList.find { it.id == toDoItem.id } != null) {
            todoList.add(getPosition(toDoItem.enabled), toDoItem.copy(id = todoList.size))
        } else {
            todoList.add(getPosition(toDoItem.enabled), toDoItem)
        }
        updateList()
    }

    private fun getPosition(toDoItemEnabled: Boolean): Int {
        return if (toDoItemEnabled) {
            0
        } else {
            todoList.size
        }
    }

    override fun editToDoItem(newToDoItem: ToDoItem) {
        removeToDoItem(getToDoItem(newToDoItem.id).id)
        addToDoItem(newToDoItem)
    }

    override fun getToDoItem(toDoItemId: Int): ToDoItem {
        return todoList.find {
            it.id == toDoItemId
        } ?: throw RuntimeException("todoItem id $toDoItemId not found")
    }

    override fun getToDoList(): LiveData<List<ToDoItem>> {
        return todoListLD
    }

    override fun removeToDoItem(toDoItemId: Int) {
        todoList.remove(getToDoItem(toDoItemId))
        updateList()
    }

    private fun updateList() {
        todoListLD.value = todoList.toList()
    }
}