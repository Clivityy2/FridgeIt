package com.example.fridgeit2.repository

import com.example.fridgeit2.data.Item
import com.example.fridgeit2.data.ItemDAO

class ItemRepository(private val dao: ItemDAO) {

    val items = dao.getAllItems()

    suspend fun upsertItem(item: Item) {
        dao.upsertItem(item)
    }

    suspend fun delete(item: Item) {
        dao.deleteItem(item)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}