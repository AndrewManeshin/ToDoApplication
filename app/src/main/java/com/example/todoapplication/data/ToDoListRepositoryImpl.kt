package com.example.todoapplication.data


import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapplication.R
import com.example.todoapplication.data.sqlite.AppSQLiteContract.ToDoItemsTable
import com.example.todoapplication.data.sqlite.AppSQLiteHelper
import com.example.todoapplication.domain.ToDoItem
import com.example.todoapplication.domain.ToDoListRepository
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.ArrayList

object ToDoListRepositoryImpl : ToDoListRepository {

    private lateinit var applicationContext: Context

    private val todoListLD = MutableLiveData<List<ToDoItem>>()

    private val database: SQLiteDatabase by lazy<SQLiteDatabase> {
        AppSQLiteHelper(applicationContext).writableDatabase
    }

    override fun addToDoItem(toDoItem: ToDoItem) {
        database.insertOrThrow(
            ToDoItemsTable.TABLE_NAME,
            null,
            contentValuesOf(
                ToDoItemsTable.COLUMN_NAME to toDoItem.name,
                ToDoItemsTable.COLUMN_IS_ENABLED to toDoItem.isEnabled.toInt(),
            )
        )
        updateToDoList()
    }

    override fun editToDoItem(newToDoItem: ToDoItem) {
        database.update(
            ToDoItemsTable.TABLE_NAME,
            contentValuesOf(
                ToDoItemsTable.COLUMN_NAME to newToDoItem.name,
                ToDoItemsTable.COLUMN_IS_ENABLED to newToDoItem.isEnabled.toInt()
            ),
            "${ToDoItemsTable.COLUMN_ID} = ?",
            arrayOf(newToDoItem.id.toString())
        )
        updateToDoList()
    }

    override fun getToDoItem(toDoItemId: Int): ToDoItem {
        val cursor = database.query(
            ToDoItemsTable.TABLE_NAME,
            arrayOf(
                ToDoItemsTable.COLUMN_ID,
                ToDoItemsTable.COLUMN_NAME,
                ToDoItemsTable.COLUMN_IS_ENABLED
            ),
            "${ToDoItemsTable.COLUMN_ID} = ?",
            arrayOf(toDoItemId.toString()),
            null, null, null
        )
        return cursor.use {
            if (cursor.count == 0) {
                throw RuntimeException(
                    applicationContext.getString(R.string.id_not_found, toDoItemId)
                )
            }
            cursor.moveToFirst()
            responseToToDoItem(cursor)
        }
    }

    override fun getToDoList(): LiveData<List<ToDoItem>> {
        return todoListLD
    }

    override fun removeToDoItem(toDoItemId: Int) {
        database.delete(
            ToDoItemsTable.TABLE_NAME,
            "${ToDoItemsTable.COLUMN_ID} = ?",
            arrayOf(toDoItemId.toString())
        )
        updateToDoList()
    }

    override fun moveToDoItem(toDoItem: ToDoItem, moveBy: Int) {
        var todoList = todoListLD.value!!
        val oldIndex = todoList.indexOfFirst { it.id == toDoItem.id }
        if (oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= todoList.size) return
        todoList = ArrayList(todoList)
        Collections.swap(todoList, oldIndex, newIndex)
        todoListLD.value = todoList.toList()
    }

    override fun updateToDoList() {
        todoListLD.value = getToDoListDB()
    }

    private fun getToDoListDB(): List<ToDoItem> {
        val cursor = database.query(
            ToDoItemsTable.TABLE_NAME,
            arrayOf(
                ToDoItemsTable.COLUMN_ID,
                ToDoItemsTable.COLUMN_NAME,
                ToDoItemsTable.COLUMN_IS_ENABLED
            ), null, null, null, null, null
        )
        cursor.use {
            val list = mutableListOf<ToDoItem>()
            if (cursor.count == 0) return listOf()
            while (cursor.moveToNext()) {

                val toDoItem = responseToToDoItem(cursor)

                if (toDoItem.isEnabled) {
                    list.add(0, toDoItem)
                } else {
                    val index = list.indexOfFirst { !it.isEnabled }
                    if (index == -1) {
                        list.add(toDoItem)
                    } else {
                        list.add(index, toDoItem)
                    }
                }
            }
            return list
        }
    }

    private fun responseToToDoItem(cursor: Cursor): ToDoItem {
        val idFromDB = cursor.getInt(cursor.getColumnIndexOrThrow(ToDoItemsTable.COLUMN_ID))
        val nameFromDB =
            cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemsTable.COLUMN_NAME))
        val statusFromDB =
            cursor.getInt(cursor.getColumnIndexOrThrow(ToDoItemsTable.COLUMN_IS_ENABLED)) == ITEM_IS_ENABLED
        return ToDoItem(idFromDB, nameFromDB, statusFromDB)
    }

    private fun Boolean.toInt() = if (this) 1 else 0

    fun init(context: Context) {
        applicationContext = context
    }

    private const val ITEM_IS_ENABLED = 1
}