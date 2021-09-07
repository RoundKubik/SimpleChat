package ru.kubov.feature_profile_impl.di.presentation.settings_data_and_storage

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.kubov.feature_profile_impl.presentation.settings_data_and_storage.SettingsDataAndStorageFragment

@Subcomponent(
    modules = [SettingsDataAndStorageModule::class]
)
interface SettingsDataAndStorageComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): SettingsDataAndStorageComponent
    }

    fun inject(fragment: SettingsDataAndStorageFragment)
}