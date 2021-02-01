package ru.grv.testtask.di

import ru.grv.testtask.di.component.*

open class ComponentManager(val appComponent: AppComponent) {
    private var profileComponent: ProfileComponent? = null
    private var booksComponent: BooksComponent? = null

    // region Getters
    // =============================================================================================
    fun getProfileComponent() : ProfileComponent {
        if (profileComponent == null) {
            profileComponent = DaggerProfileComponent.builder()
                .appComponent(appComponent)
                .build()
        }
        return profileComponent!!
    }

    fun getBooksComponent() : BooksComponent {
        if (booksComponent == null) {
            booksComponent = DaggerBooksComponent.builder()
                .appComponent(appComponent)
                .build()
        }
        return booksComponent!!
    }
    // =============================================================================================
    // endregion Getters


    // region Clear
    // =============================================================================================
    open fun clearProfileComponent() { profileComponent = null }

    open fun clearBooksComponent() { booksComponent = null }
    // =============================================================================================
    // endregion Clear
}

