package ru.kubov.core_utils.presentation.viewmodel

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: SimpleChatViewModelFactory): ViewModelProvider.Factory
}
