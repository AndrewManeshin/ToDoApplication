package com.example.todoapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapplication.domain.ToDoItem
import com.example.todoapplication.domain.ToDoListRepository
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.ArrayList

object ToDoListRepositoryImpl : ToDoListRepository {

    private val todoListLD = MutableLiveData<List<ToDoItem>>()
    private var todoList = mutableListOf<ToDoItem>()

    init {
        for (i in 0..10) {
            val item = ToDoItem(
                name = "Name $i",
                isEnabled = true
            )
            addToDoItem(item)
        }
    }

    override fun addToDoItem(toDoItem: ToDoItem) {
        fun add(toDoItem: ToDoItem) {
            todoList.add(toDoItem)
        }
        if (toDoItem.id == ToDoItem.UNDEFINED_ID || todoList.find { it.id == toDoItem.id } != null) {
            todoList.add(toDoItem.copy(id = todoList.size))
        } else if (toDoItem.isEnabled) {
            val index = todoList.indexOfFirst { !it.isEnabled }
            if (index == -1) {
                add(toDoItem)
            } else {
                todoList.add(index, toDoItem)
            }
        } else {
            add(toDoItem)
        }
        updateList()
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

    override fun moveToDoItem(toDoItem: ToDoItem, moveBy: Int) {
        val oldIndex = todoList.indexOfFirst { it.id == toDoItem.id }
        if (oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= todoList.size) return
        todoList = ArrayList(todoList)
        Collections.swap(todoList, oldIndex, newIndex)
        updateList()
    }

    private fun updateList() {
        todoListLD.value = todoList.toList()
    }
}