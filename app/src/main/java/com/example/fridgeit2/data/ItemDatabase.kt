package com.example.fridgeit2.data

import android.content.Context
import androidx.room.*

@Database(
    entities = [Item::class],
    version = 1
    //prev version 1 did not have type converter and saved date as string instead
    //error in version 2
)
@TypeConverters(DateTypeConverter::class)

abstract class ItemDatabase : RoomDatabase() {

    abstract val itemDAO : ItemDAO

    companion object{
        @Volatile
        private var INSTANCE : ItemDatabase? = null

        /*
        private val MIGRATION1To2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE item_table ADD COLUMN itemExpiryDate INTEGER")
            }
        }

        private val Migration2To3 = object  : Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Check if the column already exists before adding it
                val cursor = database.query("PRAGMA table_info(item_table)")
                var columnExists = false
                while (cursor.moveToNext()) {
                    var columnIndex = cursor.getColumnIndex("name")
                    if(columnIndex >= 0){
                        val columnName = cursor.getString(columnIndex)
                        if (columnName == "itemExpiryDate") {
                            columnExists = true
                            break
                        }
                    }
                }
                cursor.close()

                if (!columnExists) {
                    // Add the new column
                    database.execSQL("ALTER TABLE item_table ADD COLUMN itemExpiryDate INTEGER")

                    // Migrate data from old column to new column
                    database.execSQL("UPDATE item_table SET itemExpiryDate = itemExpiryData * 1000")

                    // Remove old column
                    database.execSQL("CREATE TABLE item_table_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, itemName TEXT NOT NULL, itemExpiryData INTEGER NOT NULL)")
                    database.execSQL("INSERT INTO item_table_new (id, itemName, itemExpiryData) SELECT id, itemName, itemExpiryData FROM item_table")
                    database.execSQL("DROP TABLE item_table")
                    database.execSQL("ALTER TABLE item_table_new RENAME TO item_table")
                }
            }
        }

         */

        fun getInstance(context: Context):ItemDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDatabase::class.java,
                        "item_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}