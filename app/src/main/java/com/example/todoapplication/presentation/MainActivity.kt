package com.example.todoapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.todoapplication.R
import com.example.todoapplication.data.ToDoListRepositoryImpl
import com.example.todoapplication.databinding.ActivityMainBinding
import com.example.todoapplication.presentation.screens.AddToDoItemFragment
import com.example.todoapplication.presentation.screens.ToDoListFragment


class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ToDoListRepositoryImpl.init(applicationContext)

        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
            .replace(R.id.fragmentContainer, AddToDoItemFragment.newInstance(null))
            .commit()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun editItem(itemId: Int) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, AddToDoItemFragment.newInstance(itemId))
            .commit()
    }
}