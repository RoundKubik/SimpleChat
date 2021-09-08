package ru.kubov.simplechat.root.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ru.kubov.feature_main_api.navigation.MainRouter
import ru.kubov.simplechat.R
import javax.inject.Inject


/**
 * Entity implements navigation between, fragments
 */
class Navigator @Inject constructor() : MainRouter {

    private companion object {
        private val TAG = Navigator::class.simpleName
    }

    /**
     * Keeps current state of NavController [NavController]
     */
    private var navController: NavController? = null

    /**
     * Keeps last attached to navigator activity
     */
    private var activity: AppCompatActivity? = null

    /**
     * Keeps last NavController fro bottom navigation
     */
    private val bottomNavController: NavController? by lazy {
        activity?.findNavController(R.id.frg_main__fcv_bottom_nav_host)
    }

    /**
     *  This variable keeps the last used NavController (main NavController or BottomNavController)
     *  and is used only to navigate back in back() function.
     *  @see back()
     * */
    private var lastUsedNavController: NavController? = null

    /**
     *  Method provides attaching navigation controller [NavController] and activity [AppCompatActivity]
     *
     *  @param navController - main navigation controller
     */
    fun attach(activity: AppCompatActivity, navController: NavController) {
        this.activity = activity
        this.navController = navController
    }

    /**
     *  Method provides detach navigation controller [NavController] and activity [AppCompatActivity]
     *  Always use when current activity not used. It should be called for example when activity destroyed in callback
     *  @see [AppCompatActivity.onDestroy]
     *
     */
    fun detach() {
        activity = null
        navController = null
    }

    /**
     * Implements back navigation between fragments
     */
    override fun back() {
        val popped = lastUsedNavController!!.popBackStack()
        if (!popped && lastUsedNavController == navController) {
            activity!!.finish()
        }
    }

    override fun openEditProfileScreen() {
        TODO("Not yet implemented")
    }

    override fun openSettingsThemeScreen() {
        TODO("Not yet implemented")
    }
}