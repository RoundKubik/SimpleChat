package ru.kubov.simplechat.di.root.simple_chat

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.kubov.core_utils.presentation.viewmodel.ViewModelKey
import ru.kubov.core_utils.presentation.viewmodel.ViewModelModule
import ru.kubov.simplechat.root.presentation.simple_chat.SimpleChatViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class SimpleChaActivityModule {

    @Provides
    @IntoMap
    @ViewModelKey(SimpleChatViewModel::class)
    fun provideViewModel(): ViewModel {
        return SimpleChatViewModel()
    }

    @Provides
    fun provideViewModelCreator(activity: AppCompatActivity, viewModelFactory: ViewModelProvider.Factory): SimpleChatViewModel {
        return ViewModelProvider(activity, viewModelFactory).get(SimpleChatViewModel::class.java)
    }
}
