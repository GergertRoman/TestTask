package ru.grv.testtask.di.module

import android.content.Context
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
    fun provideDatabase() = TestTaskDatabase.getDatabase(context)
}
