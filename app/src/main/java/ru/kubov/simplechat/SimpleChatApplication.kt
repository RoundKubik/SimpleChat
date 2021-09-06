package ru.kubov.simplechat

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import ru.kubov.feature_profile_impl.di.module.MainFeatureComponentHolder
import ru.kubov.simplechat.di.app.AppComponent
import ru.kubov.simplechat.di.app.DaggerAppComponent
import ru.kubov.simplechat.di.root.RootComponentHolder

/**
 * Base application
 */
class SimpleChatApplication : Application() {

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

    private fun initDeps() {
        RootComponentHolder.init()
        MainFeatureComponentHolder.init()
    }
}
