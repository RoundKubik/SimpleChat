package ru.kubov.feature_profile_impl.presentation.edit_profile

import androidx.fragment.app.Fragment
import ru.kubov.feature_profile_impl.di.module.MainFeatureComponent
import ru.kubov.feature_profile_impl.di.module.MainFeatureComponentHolder

class EditProfileFragment : Fragment() {


    private fun inject() {
        (MainFeatureComponentHolder.get() as MainFeatureComponent)
            .editProfileComponentFactory()
            .create(this)
            .inject(this)
    }
}