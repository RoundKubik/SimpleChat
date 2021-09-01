package ru.kubov.simplechat.di.deps

import dagger.Module

@Module
interface ComponentHolderModule {

    /** TODO: 01.09.2021 add binds or provides methods for feature
    example
    @Singleton
    @Provides
    fun provideAntitheftFeatureDependencies(featurePurchase: PurchaseFeatureApi): AntitheftFeatureDependencies {
    return object : AntitheftFeatureDependencies {
    override fun dbClient(): DbClient = CoreDbComponent.get().dbClient()

    override fun httpClient(): HttpClient = CoreNetworkComponent.get().httpClient()

    override fun purchaseInteractor(): PurchaseInteractor = featurePurchase.purchaseInteractor()
    }
    }

     **/
}