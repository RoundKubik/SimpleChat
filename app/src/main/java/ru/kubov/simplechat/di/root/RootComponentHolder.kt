package ru.kubov.simplechat.di.root

import ru.kubov.module_injection.holder.ComponentHolder
import javax.inject.Singleton

@Singleton
object RootComponentHolder : ComponentHolder<RootApi, RootDependencies>() {

    override fun initializeDependencies(): RootApi {
        val rootFeatureDependencies = DaggerRootComponent_RootFeatureDependenciesComponent.builder()
            .build()
        return DaggerRootComponent
            .factory()
            .create(rootFeatureDependencies)
    }
}