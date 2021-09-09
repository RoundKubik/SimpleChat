package ru.kubov.feature_main_api.navigation

/**
 * Contract for declaration of navigation methods in main feature module
 */
interface MainFeatureRouter {

    /**
     * Back navigation on previous screen
     */
    fun back()

    /**
     * Navigation to edition fragment main info
     */
    fun openEditProfileScreen()

    /**
     * Navigation to screen with settings of image storage and data
     */
    fun openSettingsStorageAndData()
}