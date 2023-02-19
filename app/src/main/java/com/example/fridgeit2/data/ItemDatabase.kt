package com.example.fridgeit2.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Item::class],
    version = 2
    //prev version 1 did not have type converter and saved date as string instead
)
@TypeConverters(DateTypeConverter::class)

abstract class ItemDatabase : RoomDatabase() {

    abstract val itemDAO : ItemDAO

    companion object{
        @Volatile
        private var INSTANCE : ItemDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE item_table ADD COLUMN itemExpiryDate INTEGER")
            }
        }
        fun getInstance(context: Context):ItemDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDatabase::class.java,
                        "item_database"
                    ).addMigrations(MIGRATION_1_2).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}