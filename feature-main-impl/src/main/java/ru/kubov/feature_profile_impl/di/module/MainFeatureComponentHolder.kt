package ru.kubov.feature_profile_impl.di.module

import ru.kubov.feature_profile_api.di.MainFeatureApi
import ru.kubov.feature_profile_api.di.MainFeatureDependencies
import ru.kubov.feature_profile_api.navigation.MainRouter
import ru.kubov.module_injection.holder.ComponentHolder
import javax.inject.Inject
import javax.inject.Singleton

object MainFeatureComponentHolder : ComponentHolder<MainFeatureApi, MainFeatureDependencies>() {

    override fun initializeDependencies(): MainFeatureApi {
        val mainFeatureDependencies = DaggerMainFeatureComponent_MainFeatureDependenciesComponent.builder()
            .build()

        return DaggerMainFeatureComponent.factory().create(mainFeatureDependencies)
    }
}