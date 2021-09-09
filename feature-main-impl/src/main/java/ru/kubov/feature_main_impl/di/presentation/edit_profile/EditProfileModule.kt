package ru.kubov.feature_main_impl.di.presentation.edit_profile

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.kubov.core_utils.presentation.viewmodel.ViewModelKey
import ru.kubov.core_utils.presentation.viewmodel.ViewModelModule
import ru.kubov.feature_main_api.navigation.MainFeatureRouter
import ru.kubov.feature_main_impl.presentation.edit_profile.EditProfileViewModel
import ru.kubov.feature_main_impl.presentation.profile.ProfileViewModel

@Module(
    includes = [ViewModelModule::class]
)
class EditProfileModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun provideViewModel(
        router: MainFeatureRouter?
    ): ViewModel {
        return EditProfileViewModel(router)
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): ProfileViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ProfileViewModel::class.java)
    }
}