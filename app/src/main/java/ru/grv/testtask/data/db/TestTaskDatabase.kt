package ru.grv.testtask.data.db

import androidx.room.*
import ru.grv.testtask.data.db.unitlocalized.BookDao
import ru.grv.testtask.data.db.unitlocalized.ProfileDao
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity

@Database(entities = [ProfileEntity::class, BookEntity::class], version = 2, exportSchema = false)
abstract class TestTaskDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun bookDao(): BookDao
}