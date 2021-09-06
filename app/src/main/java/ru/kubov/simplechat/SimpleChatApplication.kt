package ru.kubov.simplechat

import android.app.Application
import ru.kubov.feature_profile_impl.di.module.MainFeatureComponentHolder
import ru.kubov.module_injection.holder.ComponentHolder
import ru.kubov.simplechat.di.app.AppComponent
import ru.kubov.simplechat.di.app.DaggerAppComponent
import ru.kubov.simplechat.di.root.RootComponentHolder

/**
 * Base application
 */
class SimpleChatApplication : Application() {

    /**
     * Keeps application component for provides app dependencies
     * @see [AppComponent]
     */
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDeps()
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)
    }

    /**
     * Init dependencies for all modules included in application by calling [ComponentHolder.init]
     * and abstract method [ComponentHolder.initializeDependencies]
     *
     * @see [RootComponentHolder]
     * @see [MainFeatureComponentHolder]
     */
    private fun initDeps() {
        RootComponentHolder.init()
        MainFeatureComponentHolder.init()
    }
}
