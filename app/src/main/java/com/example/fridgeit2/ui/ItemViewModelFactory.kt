package com.example.fridgeit2.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.fridgeit2.repository.ItemRepository


class ItemViewModelFactory(
    private val context: Context,
    private val repository: ItemRepository,
    private val itemRecyclerView: RecyclerView
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemViewModel::class.java)){
            return ItemViewModel(context, repository, itemRecyclerView) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}