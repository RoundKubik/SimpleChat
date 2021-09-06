package ru.kubov.simplechat.di.app

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.kubov.core_utils.di.PerApplication
import ru.kubov.simplechat.SimpleChatApplication
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @PerApplication
    fun provideContext(application: SimpleChatApplication): Context {
        return application
    }
}