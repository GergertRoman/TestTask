package ru.grv.testtask.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "book")
data class BookEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "bookId")val bookId: Int,
    @ColumnInfo(name = "nameBook")val nameBook: String,
    @ColumnInfo(name = "imageBookUrl")val imageBookUrl: String,
    @ColumnInfo(name = "authors")val authors: String
) : Serializable