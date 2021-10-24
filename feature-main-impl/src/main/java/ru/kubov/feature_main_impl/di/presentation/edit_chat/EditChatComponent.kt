package ru.kubov.feature_main_impl.di.presentation.edit_chat

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.kubov.feature_main_impl.presentation.edit_chat.EditChatFragment

@Subcomponent(
    modules = [EditChatModule::class]
)
interface EditChatComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): EditChatComponent
    }

    fun inject(fragment: EditChatFragment)
}