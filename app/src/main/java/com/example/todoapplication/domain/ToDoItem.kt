package com.example.todoapplication.domain

data class ToDoItem(
    var id: Int = UNDEFINED_ID,
    val name: String = DEFAULT_NAME,
    val isEnabled: Boolean = true,
) {
    companion object {
        const val DEFAULT_NAME = "DEFAULT_NAME"
        const val UNDEFINED_ID = -1
    }
}