package com.example.todoapplication.presentation

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.CustomPopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.databinding.ItemTodoBinding
import com.example.todoapplication.domain.ToDoItem

interface ToDoActionListener {

    fun onChangeEnabledState(toDoItem: ToDoItem)

    fun onToDoDelete(toDoItemId: Int)

    fun onToDoMove(toDoItem: ToDoItem, moveBy: Int)

}

class ToDoItemsDiffCallBack(
    private val oldList: List<ToDoItem>,
    private val newList: List<ToDoItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser == newUser
    }
}


class ToDoListAdapter(
    private val actionListener: ToDoActionListener
) : RecyclerView.Adapter<ToDoListAdapter.ToDoItemViewHolder>(), View.OnClickListener {

    var toDoItems = emptyList<ToDoItem>()
        set(newValue) {
            val diffCallBack = ToDoItemsDiffCallBack(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onClick(v: View) {
        val toDoItem = v.tag as ToDoItem
        when (v.id) {
            R.id.bt_check_circle -> {
                actionListener.onChangeEnabledState(toDoItem)
            }
            R.id.bt_more -> {
                showPopupMenu(v)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(inflater, parent, false)

        binding.btCheckCircle.setOnClickListener(this)
        binding.btMore.setOnClickListener(this)

        return ToDoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        val toDoItem = toDoItems[position]
        with(holder.binding) {

            btCheckCircle.tag = toDoItem
            btMore.tag = toDoItem


            if (!toDoItem.enabled) {
                cvTodoItem.cardElevation = 1f
                llTodoItem.setBackgroundResource(toDoItem.color.rgb)
                btCheckCircle.setImageResource(R.drawable.ic_radio_button_disabled_24)
            } else {
                cvTodoItem.cardElevation = 8f
                llTodoItem.setBackgroundResource(toDoItem.color.rgb)
                btCheckCircle.setImageResource(R.drawable.ic_radio_button_enabled_24)
            }
            tvName.text = toDoItem.name
        }
    }

    override fun getItemCount() = toDoItems.size

    private fun showPopupMenu(view: View) {
        val popupMenu = CustomPopupMenu(view.context, view)
        val context = view.context
        val todoItem = view.tag as ToDoItem
        val position = toDoItems.indexOfFirst { it.id == todoItem.id }

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).apply {
            isEnabled = position > 0
            setIcon(R.drawable.ic_up_24)
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down))
            .apply {
                isEnabled = position < toDoItems.size - 1
                setIcon(R.drawable.ic_down_24)
            }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove)).apply {
            setIcon(R.drawable.ic_remove_24)
        }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> {
                    actionListener.onToDoMove(todoItem, -1)
                }
                ID_MOVE_DOWN -> {
                    actionListener.onToDoMove(todoItem, 1)
                }
                ID_REMOVE -> {
                    actionListener.onToDoDelete(todoItem.id)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    class ToDoItemViewHolder(
        val binding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}