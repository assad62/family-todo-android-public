package com.mohammadassad.familytodo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Task
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mohammadassad.familytodo.views.Events
import com.mohammadassad.familytodo.views.MyTasksScreen
import com.mohammadassad.familytodo.views.Profile

sealed class BottomNavigationRoutes(var route: String, var icon: ImageVector, var title: String) {
    object MyTasks : BottomNavigationRoutes("my_tasks", Icons.Default.Task, "My Tasks")
    object Events : BottomNavigationRoutes("events", Icons.Default.Event, "Events")
    object Profile: BottomNavigationRoutes("profile", Icons.Default.People, "Profile")
}


@Composable
fun BottomNavigationRoutes(navController: NavHostController, parentNavController: NavController){

    NavHost(navController = navController, startDestination = BottomNavigationRoutes.MyTasks.route ){

        composable(BottomNavigationRoutes.MyTasks.route) {
            MyTasksScreen()
        }
        composable(BottomNavigationRoutes.Events.route) {
            Events()
        }
        composable(BottomNavigationRoutes.Profile.route) {
            Profile(parentNavController)
        }

    }

}
