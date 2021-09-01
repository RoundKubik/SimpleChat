package ru.kubov.simplechat.di.deps

import dagger.Module
import dagger.Provides
import ru.kubov.simplechat.root.navigation.Navigator
import javax.inject.Singleton

@Module
interface NavigationModule {

    @Singleton
    @Provides
    fun provideNavigator(): Navigator = Navigator()
}