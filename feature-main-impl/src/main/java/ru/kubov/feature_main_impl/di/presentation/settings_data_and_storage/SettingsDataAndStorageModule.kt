package ru.kubov.feature_main_impl.di.presentation.settings_data_and_storage

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kubov.core_di.presentation.viewmodel.ViewModelKey
import com.kubov.core_di.presentation.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.kubov.feature_main_api.navigation.MainFeatureRouter
import ru.kubov.feature_main_impl.presentation.profile.ProfileViewModel
import ru.kubov.feature_main_impl.presentation.settings_data_and_storage.SettingsDataAndStorageViewModel

@Module(
    includes = [ViewModelModule::class]
)
class SettingsDataAndStorageModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun provideViewModel(
        router: MainFeatureRouter?
    ): ViewModel {
        return SettingsDataAndStorageViewModel(router)
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): ProfileViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ProfileViewModel::class.java)
    }
}