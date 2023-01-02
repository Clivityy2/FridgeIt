package com.example.fridgeit2.data

class ItemRepository(private val dao: ItemDAO) {

    val items = dao.getAllItems()

    suspend fun insert(item: Item) {
        dao.insertItem(item)
    }

    suspend fun update(item: Item) {
        dao.updateItem(item)
    }

    suspend fun delete(item: Item) {
        dao.deleteItem(item)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}