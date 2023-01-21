package com.example.fridgeit2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fridgeit2.R
import com.example.fridgeit2.data.Item
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

        if(repository != null) {
            Log.d("Repo", "Repo is not null")
            val factory = ItemViewModelFactory(repository)
            itemViewModel = ViewModelProvider(this, factory).get(ItemViewModel::class.java)
            binding.itemViewModel = itemViewModel
            binding.lifecycleOwner = this
        } else {
            Log.d("Repo", "Repo is  null")
        }

        binding.itemViewModel = itemViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        val btnAddItem = binding.btnAddItem
        val btnDeleteAllItems = binding.btnDeleteAllItems

        btnAddItem.setOnClickListener {
            val itemName = binding.etItemName.text.toString()
            val expiryDate = binding.etExpiryDate.text.toString()
            if(itemName.isNotEmpty() and expiryDate.isNotEmpty()) {
                val item = Item(null ,expiryDate,itemName)
                Log.d("ITEM", item.toString())
                itemViewModel.upsertItem(item)
                binding.etExpiryDate.text.clear()
                binding.etItemName.text.clear()
            }
        }
        btnDeleteAllItems.setOnClickListener{
            itemViewModel.deleteAllItems()
        }
    }

    private fun initRecyclerView() {
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        displayItemList()

    }

    private fun displayItemList() {
        itemViewModel.items.observe(this, Observer {
            binding.rvItems.adapter = ItemRecyclerViewAdapter(it)
        })
    }

}
