package ru.kubov.feature_main_impl.di.module

import ru.kubov.feature_main_api.di.MainFeatureApi
import ru.kubov.feature_main_api.di.MainFeatureDependencies
import ru.kubov.module_injection.holder.ComponentHolder

object MainFeatureComponentHolder : ComponentHolder<MainFeatureApi, MainFeatureDependencies>() {

    override fun initializeDependencies(): MainFeatureApi {
        val mainFeatureDependencies =
            DaggerMainFeatureComponent_MainFeatureDependenciesComponent.builder().build()

        return DaggerMainFeatureComponent.factory()
            .create(mainFeatureDependencies)
    }
}