package ru.kubov.feature_main_impl.di.presentation.search

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kubov.core_di.presentation.viewmodel.ViewModelKey
import com.kubov.core_di.presentation.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.kubov.feature_main_impl.presentation.profile.ProfileViewModel
import ru.kubov.feature_main_impl.presentation.search.SearchViewModel

@Module(
    includes = [ViewModelModule::class]
)
class SearchModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun provideViewModel(
    ): ViewModel {
        return SearchViewModel()
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): SearchViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(SearchViewModel::class.java)
    }
}