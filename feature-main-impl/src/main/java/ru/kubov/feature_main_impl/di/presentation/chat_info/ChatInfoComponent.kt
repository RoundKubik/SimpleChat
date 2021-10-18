package ru.kubov.feature_main_impl.di.presentation.chat_info

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.kubov.feature_main_impl.di.presentation.chats.ChatsModule
import ru.kubov.feature_main_impl.presentation.chat_info.ChatInfoFragment

@Subcomponent(
    modules = [ChatsModule::class]
)
interface ChatInfoComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ChatInfoComponent
    }

    fun inject(fragment: ChatInfoFragment)
}