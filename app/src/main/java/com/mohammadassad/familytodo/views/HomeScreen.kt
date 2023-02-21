package com.mohammadassad.familytodo.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mohammadassad.familytodo.BottomNavigationRoutes
import com.mohammadassad.familytodo.Screen
import com.mohammadassad.familytodo.model.MenuItem
import com.mohammadassad.familytodo.views.composables.AppBar
import com.mohammadassad.familytodo.views.composables.BottomNavigationBar
import com.mohammadassad.familytodo.views.composables.DrawerContent
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(parentNavController: NavController, homeViewModel: HomeViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val drawerItemClickListener: ((item: MenuItem) -> Unit) = {
        parentNavController.navigate(Screen.LoginScreen.route) {
            homeViewModel.clearToken();
            popUpTo(Screen.HomeScreen.route) {
                inclusive = true

            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerContent(drawerItemClickListener)
        },
        content = { padding ->
            Box(Modifier.padding(padding)) {
                BottomNavigationRoutes(navController = navController, parentNavController = parentNavController)
            }
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    )
}







