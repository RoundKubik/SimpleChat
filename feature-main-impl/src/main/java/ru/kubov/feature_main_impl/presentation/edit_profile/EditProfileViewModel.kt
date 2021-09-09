package ru.kubov.feature_main_impl.presentation.edit_profile

import androidx.lifecycle.ViewModel
import ru.kubov.feature_main_api.navigation.MainFeatureRouter
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val router: MainFeatureRouter?
): ViewModel() {

    fun back() {
        router?.back()
    }
}