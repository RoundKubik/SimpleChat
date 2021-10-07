package ru.kubov.feature_main_impl.di.presentation.chats

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kubov.core_di.presentation.viewmodel.ViewModelKey
import com.kubov.core_di.presentation.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.kubov.feature_main_impl.presentation.chats.ChatsViewModel

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