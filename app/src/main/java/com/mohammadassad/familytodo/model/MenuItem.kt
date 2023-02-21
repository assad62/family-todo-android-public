package com.mohammadassad.familytodo.model

import androidx.compose.ui.graphics.vector.ImageVector


sealed class DrawerRoutes(val route:String){
    object LogoutRoute: DrawerRoutes(route = "logout_route")
}

data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector,
    val route: DrawerRoutes
)
