package ru.kubov.feature_main_impl.di.presentation.chat_info

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kubov.core_di.presentation.viewmodel.ViewModelKey
import com.kubov.core_di.presentation.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.kubov.feature_main_impl.presentation.chat_info.ChatInfoViewModel

@Module(
    includes = [ViewModelModule::class]
)
class ChatInfoModule {

    @Provides
    @IntoMap
    @ViewModelKey(ChatInfoViewModel::class)
    fun provideViewModel(
    ): ViewModel {
        return ChatInfoViewModel()
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): ChatInfoViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ChatInfoViewModel::class.java)
    }
}