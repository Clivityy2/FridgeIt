package com.example.fridgeit2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fridgeit2.repository.ItemRepository


class ItemViewModelFactory(
    private val repository: ItemRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemViewModel::class.java)){
            return ItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}

/*PREV VIEW MODEL FACTORY
class ItemViewModelFactory(
    private val repository: ItemRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemViewModel::class.java)){
            return ItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}
 */