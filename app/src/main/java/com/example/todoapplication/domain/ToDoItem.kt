package com.example.todoapplication.domain

import com.example.todoapplication.R

data class ToDoItem(
    var id: Int = UNDEFINED_ID,
    val name: String,
    val enabled: Boolean,
    val color: ToDoColors = ToDoColors.EN_COLOR_1,
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}

enum class ToDoColors(val rgb: Int) {
    EN_COLOR_1(R.color.item_color_enabled_1),
    EN_COLOR_2(R.color.item_color_enabled_2),
    EN_COLOR_3(R.color.item_color_enabled_3),
    EN_COLOR_4(R.color.item_color_enabled_4),

    DIS_COLOR_1(R.color.item_color_disabled_1),
    DIS_COLOR_2(R.color.item_color_disabled_2),
    DIS_COLOR_3(R.color.item_color_disabled_3),
    DIS_COLOR_4(R.color.item_color_disabled_4),
}