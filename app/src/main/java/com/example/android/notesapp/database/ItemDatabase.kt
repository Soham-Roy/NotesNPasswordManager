package com.example.android.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Item::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun getItemDao() : ItemDao

    companion object {
        var INSTANCE : ItemDatabase ?= null

        fun getDatabase(context: Context): ItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "item_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }

}