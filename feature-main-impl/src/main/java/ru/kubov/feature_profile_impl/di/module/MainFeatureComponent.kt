package ru.kubov.feature_profile_impl.di.module

import dagger.Component
import ru.kubov.core_utils.di.PerFeature
import ru.kubov.feature_profile_api.di.MainFeatureApi
import ru.kubov.feature_profile_api.di.MainFeatureDependencies
import ru.kubov.feature_profile_impl.di.presentation.profile.ProfileComponent


@Component(
    dependencies = [MainFeatureDependencies::class],
    modules = [MainFeatureModule::class]
)
@PerFeature
interface MainFeatureComponent : MainFeatureApi {

    fun profileComponentFactory(): ProfileComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            deps: MainFeatureDependencies
        ): MainFeatureComponent
    }

    @Component(
        dependencies = [
        ]
    )
    interface MainFeatureDependenciesComponent : MainFeatureDependencies

}