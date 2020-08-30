package ru.grv.testtask.di

import ru.grv.testtask.di.component.AppComponent
import ru.grv.testtask.di.component.DaggerProfileComponent
import ru.grv.testtask.di.component.ProfileComponent

open class ComponentManager(val appComponent: AppComponent) {
    private var profileComponent: ProfileComponent? = null

    // region Getters
    //----------------------------------------------------------------------------------------------
    fun getProfileComponent() : ProfileComponent {
        if (profileComponent == null) {
            profileComponent = DaggerProfileComponent.builder()
                .appComponent(appComponent)
                .build()
        }
        return profileComponent!!
    }
    //----------------------------------------------------------------------------------------------
    // endregion Getters


    // region Clear
    //----------------------------------------------------------------------------------------------
    open fun clearProfileComponent() {
        profileComponent = null
    }
    //----------------------------------------------------------------------------------------------
    // endregion Clear
}

