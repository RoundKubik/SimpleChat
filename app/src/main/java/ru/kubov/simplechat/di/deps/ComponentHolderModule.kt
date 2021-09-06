package ru.kubov.simplechat.di.deps

import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.kubov.core_utils.di.PerApplication
import ru.kubov.feature_profile_api.di.MainFeatureApi
import ru.kubov.feature_profile_api.di.MainFeatureDependencies
import ru.kubov.feature_profile_impl.di.module.MainFeatureComponentHolder
import ru.kubov.module_injection.holder.ComponentHolder
import ru.kubov.simplechat.di.root.RootApi
import ru.kubov.simplechat.di.root.RootComponentHolder
import ru.kubov.simplechat.di.root.RootDependencies


// TODO: 05.09.2021 remove, because provides singleton of component holders 
@Module
interface ComponentHolderModule {

    @PerApplication
    @Binds
    @ClassKey(RootApi::class)
    @IntoMap
    fun provideMainFeature(rootFeatureHolder: RootComponentHolder): ComponentHolder<RootApi, RootDependencies>

    @PerApplication
    @Binds
    @ClassKey(MainFeatureApi::class)
    @IntoMap
    fun provideFeatureFeature(featureHolder: MainFeatureComponentHolder): ComponentHolder<MainFeatureApi, MainFeatureDependencies>
}