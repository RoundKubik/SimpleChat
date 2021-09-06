package ru.kubov.simplechat.di.root

import dagger.Component
import ru.kubov.core_utils.di.PerFeature
import ru.kubov.module_injection.base.BaseApi
import ru.kubov.simplechat.di.root.main.MainFragmentComponent
import ru.kubov.simplechat.di.root.simple_chat.SimpleChatActivityComponent
import javax.inject.Singleton


@Component(
    dependencies = [RootDependencies::class],
    modules = [RootModule::class]
)
@PerFeature
interface RootComponent : RootApi {

    /**
     * Provides main fragment component see [MainFragmentComponent]
     */
    fun mainFragmentComponentFactory(): MainFragmentComponent.Factory

    /**
     * Provides main application activity component [SimpleChatActivityComponent]
     */
    fun simpleChatActivityComponentFactory(): SimpleChatActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            deps: RootDependencies
        ): RootComponent
    }

    @Component(
        dependencies = []
    )
    interface RootFeatureDependenciesComponent : RootDependencies
}
