package ru.kubov.simplechat.root.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController


/**
 * Entity implements navigation between, fragments
 * **/
class Navigator {

    private var navController: NavController? = null
    private var activity: AppCompatActivity? = null

    /**
     *  Method provides attaching navigation controller [NavController] and activity [AppCompatActivity]
     *
     *  @param navController - main navigation controller
     *  @param activity - activity for navigation
     */
    fun attach(navController: NavController, activity: AppCompatActivity) {
        this.navController = navController
        this.activity = activity
    }

    /**
     *  Method provides detach navigation controller [NavController] and activity [AppCompatActivity]
     *
     */
    fun detach() {
        navController = null
        activity = null
    }
}