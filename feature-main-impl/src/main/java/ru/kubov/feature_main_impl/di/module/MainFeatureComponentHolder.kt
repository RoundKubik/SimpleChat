package ru.kubov.feature_main_impl.di.module

import ru.kubov.feature_main_api.di.MainFeatureApi
import ru.kubov.feature_main_api.di.MainFeatureDependencies
import ru.kubov.feature_main_api.navigation.MainFeatureRouter
import ru.kubov.module_injection.holder.ComponentHolder

object MainFeatureComponentHolder : ComponentHolder<MainFeatureApi, MainFeatureDependencies>() {

    private var mainFeatureRouter: MainFeatureRouter? = null

    fun initMainFeatureRouter(mainFeatureRouter: MainFeatureRouter?) {
        this.mainFeatureRouter = mainFeatureRouter
    }

    fun initWithMainFeatureRouter(mainFeatureRouter: MainFeatureRouter?) {
        this.mainFeatureRouter = mainFeatureRouter
        init()
    }

    override fun initializeDependencies(): MainFeatureApi {
        val mainFeatureDependencies =
            DaggerMainFeatureComponent_MainFeatureDependenciesComponent.builder().build()

        return DaggerMainFeatureComponent.factory()
            .create(mainFeatureRouter, mainFeatureDependencies)
    }

    override fun reset() {
        mainFeatureRouter = null
        super.reset()
    }

}

