package ru.kubov.feature_main_impl.di.presentation.search

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.kubov.feature_main_impl.di.presentation.edit_profile.EditProfileModule
import ru.kubov.feature_main_impl.presentation.search.SearchFragment

@Subcomponent(
    modules = [
        EditProfileModule::class,
    ]
)
interface SearchComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): SearchComponent
    }

    fun inject(fragment: SearchFragment)
}