package com.example.todoapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoapplication.R
import com.example.todoapplication.databinding.ActivityMainBinding
import com.example.todoapplication.domain.ToDoItem
import com.example.todoapplication.presentation.screens.AddToDoItemFragment
import com.example.todoapplication.presentation.screens.ToDoListFragment


class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, ToDoListFragment())
                .commit()
        }
    }

    override fun addItem() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, AddToDoItemFragment())
            .commit()
    }

    override fun goBack() {
        onBackPressed()
    }
}