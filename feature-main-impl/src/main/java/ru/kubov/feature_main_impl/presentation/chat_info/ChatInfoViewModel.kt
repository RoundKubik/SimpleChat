package ru.kubov.feature_main_impl.presentation.chat_info

import androidx.lifecycle.ViewModel
import ru.kubov.feature_main_api.navigation.MainFeatureRouter
import javax.inject.Inject

// TODO: 20.10.2021 add documentation 
class ChatInfoViewModel @Inject constructor(
    private val router: MainFeatureRouter?
) : ViewModel() {

    // TODO: 01.11.2021  
    fun navigateToEditChatScreen() {
        router?.openEditChatScreen()
    }
}