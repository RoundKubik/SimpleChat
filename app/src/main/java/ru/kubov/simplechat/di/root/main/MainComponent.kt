package ru.kubov.simplechat.di.root.main

import androidx.fragment.app.FragmentActivity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.kubov.simplechat.root.presentation.main.MainFragment

/*@Subcomponent(
    modules = [
        MainFragmentModule::class
    ]
)*/
// TODO: 02.09.2021 check and remove
//@ScreenScope
interface MainFragmentComponent {

   /* @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance activity: FragmentActivity
        ): MainFragmentComponent
    }

    fun inject(fragment: MainFragment)*/
}