package ru.grv.testtask.di

import android.content.Context
import ru.grv.testtask.di.component.DaggerAppComponent
import ru.grv.testtask.di.module.AppModule

object DaggerWrapper {
    private var componentManager: ComponentManager? = null

    fun getComponentManager(context: Context): ComponentManager? {
        if (componentManager == null) {
            val appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(context))
                .build()
            componentManager = ComponentManager(appComponent)
        }

        return componentManager
    }
}
