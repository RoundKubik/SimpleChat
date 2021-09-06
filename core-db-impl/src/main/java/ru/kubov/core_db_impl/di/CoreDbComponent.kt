package ru.kubov.core_db_impl.di

import dagger.Component
import ru.kubov.core_db_api.di.CoreDbApi
import javax.inject.Singleton

@Component(modules = [DbModule::class])
@Singleton
interface CoreDbComponent : CoreDbApi