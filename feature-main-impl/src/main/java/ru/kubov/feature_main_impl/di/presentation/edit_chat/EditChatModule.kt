package ru.kubov.feature_main_impl.di.presentation.edit_chat

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kubov.core_di.presentation.viewmodel.ViewModelKey
import com.kubov.core_di.presentation.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.kubov.feature_main_impl.presentation.edit_chat.EditChatViewModel


@Module(
    includes = [ViewModelModule::class]
)
class EditChatModule {

    @Provides
    @IntoMap
    @ViewModelKey(EditChatViewModel::class)
    fun provideViewModel(
    ): ViewModel {
        return EditChatViewModel()
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): EditChatViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(EditChatViewModel::class.java)
    }
}