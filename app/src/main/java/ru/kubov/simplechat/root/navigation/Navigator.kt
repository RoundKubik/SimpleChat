package ru.kubov.simplechat.root.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController


/**
 * Entity implements navigation between, fragments
 * **/
class Navigator {

    private var navController: NavController? = null

    /**
     *  Method provides attaching navigation controller [NavController] and activity [AppCompatActivity]
     *
     *  @param navController - main navigation controller
     */
    fun attach(navController: NavController) {
        this.navController = navController
    }

    /**
     *  Method provides detach navigation controller [NavController] and activity [AppCompatActivity]
     *
     */
    fun detach() {
        navController = null
    }
}