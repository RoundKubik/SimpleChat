package ru.kubov.feature_profile_impl.presentation.settings_data_and_storage

import androidx.lifecycle.ViewModel
import com.facebook.drawee.backends.pipeline.Fresco
import javax.inject.Inject

// TODO: 07.09.2021 provide in contructor navigation router
class SettingsDataAndStorageViewModel @Inject constructor(): ViewModel() {
    
    fun back() {
        
    }

    fun clearImageData() {
        Fresco.getImagePipeline().clearCaches()
    }

}