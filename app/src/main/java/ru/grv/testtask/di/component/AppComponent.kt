package ru.grv.testtask.di.component

import android.content.Context
import dagger.Component
import ru.grv.testtask.data.db.TestTaskDatabase
import ru.grv.testtask.di.module.AppModule

import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun context(): Context
    fun testTaskDatabase(): TestTaskDatabase
}