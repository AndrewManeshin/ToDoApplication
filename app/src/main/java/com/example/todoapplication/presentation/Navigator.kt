package com.example.todoapplication.presentation

interface Navigator {

    fun addItem()

    fun goBack()

    fun editItem(itemId: Int)

}