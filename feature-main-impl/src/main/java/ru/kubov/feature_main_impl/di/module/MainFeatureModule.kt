package ru.kubov.feature_main_impl.di.module

import dagger.Module
import dagger.Provides
import ru.kubov.feature_main_api.navigation.MainRouter
import javax.inject.Inject
import javax.inject.Singleton

@Module
class MainFeatureModule {

    @Inject
    @Singleton
    lateinit var router: MainRouter

    @Provides
    fun mainFeatureRouter() = router
}