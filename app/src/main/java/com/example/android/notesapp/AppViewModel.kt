package com.example.android.notesapp

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.example.android.notesapp.database.Item
import com.example.android.notesapp.database.ItemDatabase
import kotlinx.coroutines.launch
import java.util.*

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val pref = PreferenceManager.getDefaultSharedPreferences(application)

    private val database : ItemDatabase = ItemDatabase.getDatabase(application)
    private val itemDao = database.getItemDao()

    val allNotes : LiveData<List<Item>> = itemDao.getAllNotes().asLiveData()
    val allPasswords : LiveData< List<Item> > = itemDao.getAllPasswords().asLiveData()

    fun getItemById(id : Int) : LiveData<Item> = itemDao.getItemById(id).asLiveData()

    private fun insertItem(item : Item){ viewModelScope.launch { itemDao.insert(item) } }

    fun updateItem(item: Item) { viewModelScope.launch { itemDao.update(item) } }

    fun deleteItem(item : Item) { viewModelScope.launch { itemDao.delete(item) } }

    fun saveItem(title : String, desc : String, isNote : Boolean) {
        val item = Item(title = title, desc = desc, isNote = isNote, date = Date())
        insertItem(item)
    }

    // LockScreen
    val pinEnabled : Boolean = pref.getBoolean("pin_lock", false)
    fun getPin() : String {
        return pref.getString("your_pin", "").toString()
    }

}