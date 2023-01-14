package com.example.fridgeit2.data


import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertItem(item: Item):Long

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM item_table")
    fun getAllItems(): Flow<List<Item>>
}