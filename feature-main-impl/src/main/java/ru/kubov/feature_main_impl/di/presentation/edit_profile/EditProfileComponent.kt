package ru.kubov.feature_main_impl.di.presentation.edit_profile

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.kubov.feature_main_impl.presentation.edit_profile.EditProfileFragment

@Subcomponent(
    modules = [
        EditProfileModule::class,
    ]
)
interface EditProfileComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): EditProfileComponent
    }

    fun inject(fragment: EditProfileFragment)
}