package ru.kubov.module_injection.holder

import ru.kubov.module_injection.base.BaseAPI
import ru.kubov.module_injection.base.BaseDependencies

interface ComponentHolder<C : BaseAPI, D : BaseDependencies> {

    fun init(dependencies: D)

    fun get(): C

    fun reset()
}



