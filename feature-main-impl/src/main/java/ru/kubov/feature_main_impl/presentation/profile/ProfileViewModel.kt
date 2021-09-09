package ru.kubov.feature_main_impl.presentation.profile

import androidx.lifecycle.ViewModel
import ru.kubov.feature_main_api.navigation.MainFeatureRouter
import javax.inject.Inject

/**
 * Class provides editing profile and show information about current user
 */
class ProfileViewModel @Inject constructor(
    private val router: MainFeatureRouter?
) : ViewModel() {

    /**
     * Navigation to edit profile screen by call router method [MainFeatureRouter.openEditProfileScreen]
     *
     * @see MainFeatureRouter
     */
    fun openEditProfileScreen() {
        router?.openEditProfileScreen()
    }

    /**
     * Navigation to settings storage and data  screen by call router method [MainFeatureRouter.openSettingsStorageAndData]
     *
     * @see MainFeatureRouter
     */
    fun openSettingsStorageAndDataScreen() {
        router?.openSettingsStorageAndData()
    }


}