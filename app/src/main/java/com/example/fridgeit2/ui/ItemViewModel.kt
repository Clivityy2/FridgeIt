package com.example.fridgeit2.ui

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.fridgeit2.data.Item
import com.example.fridgeit2.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository, private val itemRecyclerView: RecyclerView) : ViewModel(),Observable {

    val items = repository.items


    fun upsertItem(item: Item) {
        viewModelScope.launch {
            repository.upsertItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }

    fun deleteAllItems(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    val swipeToDeleteCallback = object  : SwipeToDeleteCallback(){
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val itemPosition = viewHolder.adapterPosition
            val item = items.value?.get(itemPosition)
            item?.let { deleteItem(it) }
            itemRecyclerView.adapter?.notifyItemRemoved(itemPosition)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}