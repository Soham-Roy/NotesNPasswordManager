package com.example.android.notesapp.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM items WHERE isnote = 1 ORDER BY date_updated DESC")
    fun getAllNotes() : Flow<List<Item>>

    @Query("SELECT * FROM items WHERE isnote = 0 ORDER BY date_updated DESC")
    fun getAllPasswords() : Flow<List<Item>>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id : Int) : Flow<Item>

    @Insert
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

}