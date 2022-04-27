package com.example.todoapplication.domain

data class ToDoItem(
    var id: Int = UNDEFINED_ID,
    val name: String,
    val enabled: Boolean
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}