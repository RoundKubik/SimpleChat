package ru.kubov.feature_main_impl.di.presentation.chats

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.kubov.feature_main_impl.presentation.chats.ChatsFragment

@Subcomponent(
    modules = [ChatsModule::class]
)
interface ChatsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ChatsComponent
    }

    fun inject(fragment: ChatsFragment)
}