package ru.kubov.simplechat.di.app

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.kubov.simplechat.SimpleChatApplication
import javax.inject.Singleton

@Module
interface AppModule {


    @Singleton
    @Binds
    fun bindContext(app: SimpleChatApplication): Context

    @Singleton
    @Binds
    fun bindApplication(app: SimpleChatApplication): Application


   /* @Singleton
    @Provides
    fun provideScannerFeatureDependencies(featurePurchase: PurchaseFeatureApi): ScannerFeatureDependencies {
        return object : ScannerFeatureDependencies {
            override fun dbClient(): DbClient = CoreDbComponent.get().dbClient()

            override fun httpClient(): HttpClient = CoreNetworkComponent.get().httpClient()

            override fun someUtils(): SomeUtils = CoreUtilsComponent.get().someUtils()

            override fun purchaseInteractor(): PurchaseInteractor = featurePurchase.purchaseInteractor()

        }
    }

    @Singleton
    @Provides
    fun provideAntitheftFeatureDependencies(featurePurchase: PurchaseFeatureApi): AntitheftFeatureDependencies {
        return object : AntitheftFeatureDependencies {
            override fun dbClient(): DbClient = CoreDbComponent.get().dbClient()

            override fun httpClient(): HttpClient = CoreNetworkComponent.get().httpClient()

            override fun purchaseInteractor(): PurchaseInteractor = featurePurchase.purchaseInteractor()
        }
    }

    @Singleton
    @Provides
    fun providePurchaseFeatureDependencies(): PurchaseFeatureDependencies {
        return object : PurchaseFeatureDependencies {
            override fun httpClient(): HttpClient = CoreNetworkComponent.get().httpClient()
        }
    }

    @Provides
    fun provideFeatureScanner(dependencies: ScannerFeatureDependencies): ScannerFeatureApi {
        ScannerFeatureComponentHolder.init(dependencies)
        return ScannerFeatureComponentHolder.get()
    }

    @Provides
    fun provideFeatureAntitheft(dependencies: AntitheftFeatureDependencies): AntitheftFeatureApi {
        AntitheftFeatureComponentHolder.init(dependencies)
        return AntitheftFeatureComponentHolder.get()
    }

    @Provides
    fun provideFeaturePurchase(dependencies: PurchaseFeatureDependencies): PurchaseFeatureApi {
        PurchaseComponentHolder.init(dependencies)
        return PurchaseComponentHolder.get()
    }*/
}