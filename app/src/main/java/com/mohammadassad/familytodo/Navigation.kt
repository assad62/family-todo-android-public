package com.mohammadassad.familytodo

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mohammadassad.familytodo.views.HomeScreen
import com.mohammadassad.familytodo.views.LoginScreen
import com.mohammadassad.familytodo.views.RegisterScreen
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(route = Screen.HomeScreen.route) {
            BackHandler() {
                //do nothing to force users to logout to navigate back
            }
            HomeScreen(parentNavController = navController)
        }
    }

}