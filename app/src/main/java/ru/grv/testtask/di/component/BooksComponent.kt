package ru.grv.testtask.di.component

import dagger.Component
import ru.grv.testtask.di.scopes.ModuleScope
import ru.grv.testtask.di.module.ProfileModule
import ru.grv.testtask.presentation.book.view.BooksController
import ru.grv.testtask.presentation.profile.view.ProfileController

@ModuleScope
@Component(modules = [ProfileModule::class], dependencies = [AppComponent::class])
interface BooksComponent {
    fun inject(controller: BooksController)
}
