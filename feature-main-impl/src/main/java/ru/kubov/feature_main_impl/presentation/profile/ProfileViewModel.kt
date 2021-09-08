package ru.kubov.feature_main_impl.presentation.profile

import androidx.lifecycle.ViewModel
import ru.kubov.core_utils.domain.Profile
import javax.inject.Inject

/**
 * Class provides editing profile and show information about current user
 */
class ProfileViewModel @Inject constructor() : ViewModel() {
/*
    @Inject
    lateinit var router: MainRouter

    fun back() {
        router.back()
    }

    fun openEditProfileFragment() {
        router.openEditProfileFragment()
    }*/
    //remove [lateinit] to private
    /**
     * Provides profile info see model [Profile]
     */
    /* lateinit var _profile : MutableLiveData<Profile>
     val  profile : LiveData<Profile> get() = _profile*/

}