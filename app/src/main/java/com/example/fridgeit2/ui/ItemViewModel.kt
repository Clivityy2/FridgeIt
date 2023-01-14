package com.example.fridgeit2.ui

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fridgeit2.data.Item
import com.example.fridgeit2.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel(),Observable {

    val items = repository.items

    @Bindable
    val inputItemName = MutableLiveData<String>()
    @Bindable
    val inputExpiryDate = MutableLiveData<String>()

    fun upsertItem(item: Item) {
        val expiry = inputExpiryDate.value!!
        val itemName = inputItemName.value!!
        upsertItem(Item(null,expiry,itemName))
        inputItemName.value = ""
        inputExpiryDate.value = ""
        viewModelScope.launch {
            repository.upsertItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.delete(item)
        }

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}