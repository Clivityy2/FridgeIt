package com.example.fridgeit2.ui

import android.content.Context
import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.fridgeit2.NotificationService
import com.example.fridgeit2.data.Item
import com.example.fridgeit2.repository.ItemRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

class ItemViewModel(
    private val context: Context,
    private val repository: ItemRepository,
    private val itemRecyclerView: RecyclerView
) : ViewModel(), Observable {

    private val notificationService = NotificationService(context)

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

    fun deleteAllItems() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun checkIfItemIsDueToExpire() {
        viewModelScope.launch {
            while (true) {
                val currentLocalDate = LocalDate.now()
                for (item in items.value ?: emptyList()) {
                    val newLocalDate = currentLocalDate.minusDays(item.reminderDate.toLong())

                    Log.d(
                        "CHECK ITEM EXPIRY",
                        "Item Name: ${item.itemName} Expiry Date: ${item.itemExpiryDate} NewDate: $newLocalDate ReminderDate: ${item.reminderDate}"
                    )

                    if (item.itemExpiryDate == newLocalDate)
                        notificationService.showNotification(item)
                }
                delay(24 * 60 * 60 * 1000) //wait 24hrs before checking again
            }
        }
    }

    val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val itemPosition = viewHolder.adapterPosition
            val item = items.value?.get(itemPosition)
            item?.let {
                deleteItem(it)
                itemRecyclerView.adapter?.notifyItemRemoved(itemPosition)
            }
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}