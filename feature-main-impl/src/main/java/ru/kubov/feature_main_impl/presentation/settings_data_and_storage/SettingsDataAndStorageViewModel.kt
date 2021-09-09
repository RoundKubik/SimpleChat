package ru.kubov.feature_main_impl.presentation.settings_data_and_storage

import androidx.lifecycle.ViewModel
import com.facebook.drawee.backends.pipeline.Fresco
import ru.kubov.feature_main_api.navigation.MainFeatureRouter
import javax.inject.Inject

// TODO: 07.09.2021 provide in contructor navigation router
class SettingsDataAndStorageViewModel @Inject constructor(
    private val router: MainFeatureRouter?
) : ViewModel() {

    fun back() {
        router?.back()
    }

    fun clearImageData() {
        Fresco.getImagePipeline().clearCaches()
    }
}