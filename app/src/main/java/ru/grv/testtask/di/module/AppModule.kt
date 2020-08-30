package ru.grv.testtask.di.module

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import ru.grv.testtask.data.db.TestTaskDatabase
import javax.inject.Singleton

@Module
class AppModule constructor(val context: Context) {
    @Singleton
    @Provides
    fun provideAppContext(): Context = context

    @Singleton
    @Provides
    fun providesRoomDatabase(): TestTaskDatabase {
        return Room.databaseBuilder(context, TestTaskDatabase::class.java, "testtask.db")
            .fallbackToDestructiveMigration()
            .addCallback(databaseCallback)
            .build()
    }

    @Singleton
    @Provides
    fun providesProfileDao(testTaskDatabase: TestTaskDatabase) = testTaskDatabase.profileDao()

    @Singleton
    @Provides
    fun providesBookDao(testTaskDatabase: TestTaskDatabase) = testTaskDatabase.bookDao()

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("RoomDatabaseModule", "onCreate")
        }
    }
}
