package com.example.fridgeit2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "item_table"
)
data class Item (
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "itemName") val itemName: String,
    @ColumnInfo(name = "itemExpiryData") val itemExpiryDate : Int,
)