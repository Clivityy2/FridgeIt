package com.example.fridgeit2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fridgeit2.R
import com.example.fridgeit2.data.ItemDatabase


import com.example.fridgeit2.databinding.ActivityHomeBinding
import com.example.fridgeit2.repository.ItemRepository

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)

        val dao = ItemDatabase.getInstance(application).itemDAO
        val repository = ItemRepository(dao)
        val factory = ItemViewModelFactory(repository)
        itemViewModel = ViewModelProvider(this, factory).get(ItemViewModel::class.java)
        binding.itemViewModel = itemViewModel
        binding.lifecycleOwner = this
        //displayItemList()

    }
}
