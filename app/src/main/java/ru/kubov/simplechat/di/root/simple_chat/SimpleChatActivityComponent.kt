package ru.kubov.simplechat.di.root.simple_chat

import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.kubov.core_utils.di.PerFeature
import ru.kubov.simplechat.root.presentation.simple_chat.SimpleChatActivity

@Subcomponent(
    modules = [SimpleChaActivityModule::class]
)
interface SimpleChatActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: AppCompatActivity
        ): SimpleChatActivityComponent
    }

    fun inject(rootActivity: SimpleChatActivity)
}
