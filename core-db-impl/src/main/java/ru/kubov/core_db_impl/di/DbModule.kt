package ru.kubov.core_db_impl.di

import dagger.Binds
import dagger.Module
import ru.kubov.core_db_api.data.AppDatabase
import ru.kubov.core_db_impl.data.RoomAppDatabase
import javax.inject.Singleton

@Module
internal abstract class DbModule {
   /* @Singleton
    @Binds
    abstract fun provideDbClientApi(dbClient: RoomAppDatabase): AppDatabase*/
}