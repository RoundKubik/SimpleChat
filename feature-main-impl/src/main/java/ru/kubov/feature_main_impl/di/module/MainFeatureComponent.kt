package ru.kubov.feature_main_impl.di.module

import dagger.BindsInstance
import dagger.Component
import ru.kubov.core_utils.di.PerFeature
import ru.kubov.feature_main_api.di.MainFeatureApi
import ru.kubov.feature_main_api.di.MainFeatureDependencies
import ru.kubov.feature_main_api.navigation.MainFeatureRouter
import ru.kubov.feature_main_impl.di.presentation.chat_info.ChatInfoComponent
import ru.kubov.feature_main_impl.di.presentation.chats.ChatsComponent
import ru.kubov.feature_main_impl.di.presentation.edit_profile.EditProfileComponent
import ru.kubov.feature_main_impl.di.presentation.profile.ProfileComponent
import ru.kubov.feature_main_impl.di.presentation.settings_data_and_storage.SettingsDataAndStorageComponent


@Component(
    dependencies = [MainFeatureDependencies::class],
    modules = [MainFeatureModule::class]
)
@PerFeature
interface MainFeatureComponent : MainFeatureApi {

    fun profileComponentFactory(): ProfileComponent.Factory
    fun editProfileComponentFactory(): EditProfileComponent.Factory
    fun settingsDataAndStorageComponentFactory(): SettingsDataAndStorageComponent.Factory
    fun chatsComponentFactory() : ChatsComponent.Factory
    fun chatInfoComponentFactory() : ChatInfoComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance router: MainFeatureRouter?,
            mainFeatureDeps: MainFeatureDependencies
        ): MainFeatureComponent
    }

    @Component(
        dependencies = [
        ]
    )
    interface MainFeatureDependenciesComponent : MainFeatureDependencies

}