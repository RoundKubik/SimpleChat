package ru.kubov.simplechat.di.root.main

import androidx.fragment.app.FragmentActivity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.kubov.core_utils.di.PerFeature
import ru.kubov.simplechat.root.presentation.main.MainFragment

@Subcomponent(
    modules = [
        MainFragmentModule::class
    ]
)
interface MainFragmentComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance activity: FragmentActivity
        ): MainFragmentComponent
    }

    fun inject(fragment: MainFragment)
}