package ru.grv.testtask.data.db

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import ru.grv.testtask.data.db.unitlocalized.BookDao
import ru.grv.testtask.data.db.unitlocalized.ProfileDao
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity

@Database(
    entities = [
        ProfileEntity::class,
        BookEntity::class],
    version = 2,
    exportSchema = false
)
abstract class TestTaskDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun bookDao(): BookDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TestTaskDatabase? = null

        fun getDatabase(context: Context): TestTaskDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestTaskDatabase::class.java,
                    "database.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}