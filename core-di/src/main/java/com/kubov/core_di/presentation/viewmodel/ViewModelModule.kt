package com.kubov.core_di.presentation.viewmodel

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: SimpleChatViewModelFactory): ViewModelProvider.Factory
}
