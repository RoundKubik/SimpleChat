package ru.kubov.simplechat

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.ImageTranscoderType
import com.facebook.imagepipeline.core.MemoryChunkType
import ru.kubov.feature_main_impl.di.module.MainFeatureComponentHolder
import ru.kubov.module_injection.holder.ComponentHolder
import ru.kubov.simplechat.di.app.AppComponent
import ru.kubov.simplechat.di.app.DaggerAppComponent
import ru.kubov.simplechat.di.root.RootComponentHolder

/**
 * Base application
 */
class SimpleChatApplication : Application() {

    /**
     * Keeps application component for provides app dependencies
     * @see [AppComponent]
     */
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDeps()
        initExternalLibraries()
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)
    }

    /**
     * Init dependencies for all modules included in application by calling [ComponentHolder.init]
     * and abstract method [ComponentHolder.initializeDependencies]
     *
     * @see [RootComponentHolder]
     * @see [MainFeatureComponentHolder]
     */
    private fun initDeps() {
        RootComponentHolder.init()
        MainFeatureComponentHolder.init()
    }

    private fun initExternalLibraries() {
        Fresco.initialize(
            applicationContext,
            ImagePipelineConfig.newBuilder(applicationContext)
                .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
                .experiment().setNativeCodeDisabled(true)
                .build()
        )
    }
}
