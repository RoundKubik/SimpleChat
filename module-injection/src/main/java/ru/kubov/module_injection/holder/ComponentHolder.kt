package ru.kubov.module_injection.holder

import ru.kubov.module_injection.base.BaseApi
import ru.kubov.module_injection.base.BaseDependencies
import java.util.concurrent.locks.ReentrantLock

abstract class ComponentHolder<C : BaseApi<D>, D : BaseDependencies> {

    private val featureLocker = ReentrantLock()

    private var featureComponent: C? = null

    fun init() {
        featureLocker.lock()
        if (featureComponent == null) {
            featureComponent = initializeDependencies()
        }
        featureLocker.unlock()
    }

    fun get(): C = featureComponent ?: throw RuntimeException()

    fun reset() {
        featureLocker.lock()
        featureComponent = null
        featureLocker.unlock()
    }

    abstract fun initializeDependencies(): C
}