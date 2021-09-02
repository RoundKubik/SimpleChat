package ru.kubov.simplechat

import android.app.Application
import android.content.Context
import ru.kubov.simplechat.di.app.AppComponent
import ru.kubov.simplechat.di.app.DaggerAppComponent
import javax.inject.Inject

class SimpleChatApplication : Application() {
    override fun onCreate() {
        super.onCreate()
     /*   DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)*/
    }
}
