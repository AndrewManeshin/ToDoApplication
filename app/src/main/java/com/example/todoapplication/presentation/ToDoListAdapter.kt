package com.example.todoapplication.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.databinding.ItemTodoBinding
import com.example.todoapplication.domain.ToDoItem

interface ToDoActionListener {

    fun changeEnabledState(toDoItem: ToDoItem)

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
        when(v.id) {
            R.id.bt_check_circle -> {
                actionListener.changeEnabledState(toDoItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(inflater, parent, false)

        binding.btCheckCircle.setOnClickListener(this)

        return ToDoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        val toDoItem = toDoItems[position]
        with(holder.binding) {

            btCheckCircle.tag = toDoItem


            if (!toDoItem.enabled) {
                cvTodoItem.cardElevation = 1f
                llTodoItem.setBackgroundResource(R.color.item_color_disabled_1)
                btCheckCircle.setImageResource(R.drawable.ic_radio_button_disabled_24)
            } else {
                cvTodoItem.cardElevation = 8f
                llTodoItem.setBackgroundResource(R.color.item_color_enabled_1)
                btCheckCircle.setImageResource(R.drawable.ic_radio_button_enabled_24)
            }
            tvName.text = toDoItem.name
        }
    }

    override fun getItemCount() = toDoItems.size

    class ToDoItemViewHolder(
        val binding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root)
}