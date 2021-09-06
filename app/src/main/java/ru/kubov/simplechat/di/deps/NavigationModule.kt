package ru.kubov.simplechat.di.deps

import dagger.Module
import dagger.Provides
import ru.kubov.core_utils.di.PerApplication
import ru.kubov.feature_profile_api.navigation.MainRouter
import ru.kubov.simplechat.root.navigation.Navigator
import javax.inject.Singleton

@Module
class NavigationModule {

    @PerApplication
    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @PerApplication
    @Provides
    fun provideMainRouter(navigator: Navigator): MainRouter = navigator

}