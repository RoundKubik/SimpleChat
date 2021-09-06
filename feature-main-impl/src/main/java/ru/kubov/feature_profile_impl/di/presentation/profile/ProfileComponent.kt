package ru.kubov.feature_profile_impl.di.presentation.profile

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.kubov.feature_profile_impl.presentation.profile.ProfileFragment

@Subcomponent(
    modules = [
        ProfileModule::class,
    ]
)
interface ProfileComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ProfileComponent
    }

    fun inject(fragment: ProfileFragment)
}