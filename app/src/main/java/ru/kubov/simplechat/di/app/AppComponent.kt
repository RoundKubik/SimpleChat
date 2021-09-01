package ru.kubov.simplechat.di.app

import dagger.BindsInstance
import dagger.Component
import ru.kubov.simplechat.SimpleChatApplication
import ru.kubov.simplechat.di.deps.ComponentHolderModule
import ru.kubov.simplechat.di.deps.NavigationModule
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        ComponentHolderModule::class,
        NavigationModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: SimpleChatApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: SimpleChatApplication)
}



