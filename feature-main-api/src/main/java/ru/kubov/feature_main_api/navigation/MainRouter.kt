package ru.kubov.feature_main_api.navigation

/**
 * Provides navigation between screens in main feature
 */
interface MainRouter {

    /**
     * Provides back navigation
     */
    fun back()

    /**
     * Provides navigation to open edit profile fragment
     */
    fun openEditProfileFragment()

    /**
     * Provides navigation to open settings data and cache images screen
     */
    fun openSettingsStorageAndData()

    fun openEditChatScreen()
}