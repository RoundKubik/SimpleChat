package ru.kubov.feature_main_impl.di.presentation.chats

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.kubov.core_utils.presentation.viewmodel.ViewModelKey
import ru.kubov.core_utils.presentation.viewmodel.ViewModelModule
import ru.kubov.feature_main_api.navigation.MainFeatureRouter
import ru.kubov.feature_main_impl.presentation.chat.ChatViewModel
import ru.kubov.feature_main_impl.presentation.chats.ChatsViewModel
import ru.kubov.feature_main_impl.presentation.edit_profile.EditProfileViewModel
import ru.kubov.feature_main_impl.presentation.profile.ProfileViewModel

@Module(
    includes = [ViewModelModule::class]
)
class ChatsModule {

    @Provides
    @IntoMap
    @ViewModelKey(ChatsViewModel::class)
    fun provideViewModel(
    ): ViewModel {
        return ChatsViewModel()
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): ChatsViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ChatsViewModel::class.java)
    }
}