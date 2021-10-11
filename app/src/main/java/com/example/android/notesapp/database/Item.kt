package com.example.android.notesapp.database

import androidx.room.*
import java.util.*

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "content") val desc : String,
    @ColumnInfo(name = "isnote") val isNote : Boolean,

    @ColumnInfo(name = "date_updated")
    @TypeConverters(DateConverter::class) val date : Date
)

// isNote = 0 for Password and isNote = 1 for Note