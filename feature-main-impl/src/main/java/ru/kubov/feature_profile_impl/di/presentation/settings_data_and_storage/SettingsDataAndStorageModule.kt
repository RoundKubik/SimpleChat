package ru.kubov.feature_profile_impl.di.presentation.settings_data_and_storage

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.kubov.core_utils.presentation.viewmodel.ViewModelKey
import ru.kubov.core_utils.presentation.viewmodel.ViewModelModule
import ru.kubov.feature_profile_impl.presentation.profile.ProfileViewModel
import ru.kubov.feature_profile_impl.presentation.settings_data_and_storage.SettingsDataAndStorageViewModel

@Module(
    includes = [ViewModelModule::class]
)
class SettingsDataAndStorageModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun provideViewModel(
    ): ViewModel {
        return SettingsDataAndStorageViewModel()
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): ProfileViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ProfileViewModel::class.java)
    }
}