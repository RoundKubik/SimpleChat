package ru.kubov.feature_main_impl.presentation.chats

import androidx.fragment.app.Fragment
import ru.kubov.feature_main_impl.di.module.MainFeatureComponent
import ru.kubov.feature_main_impl.di.module.MainFeatureComponentHolder

class ChatsFragment : Fragment() {

    private fun inject() {
        (MainFeatureComponentHolder.get() as MainFeatureComponent)
            .chatsComponentFactory()
            .create(this)
            .inject(this)
    }
}