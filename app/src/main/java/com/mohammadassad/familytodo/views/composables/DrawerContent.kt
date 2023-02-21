package com.mohammadassad.familytodo.views.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohammadassad.familytodo.model.DrawerRoutes
import com.mohammadassad.familytodo.model.MenuItem
import com.mohammadassad.familytodo.views.DrawerHeader

@Composable
fun DrawerContent(

    drawerItemClickListener: ((item: MenuItem) -> Unit)?
) {
    Column(Modifier.fillMaxSize()) {
        DrawerHeader()
        DrawerBody(
            items = listOf(
                MenuItem(
                    id = "log out",
                    title = "Log out",
                    contentDescription = "LogOut",
                    icon = Icons.Default.Logout,
                    route = DrawerRoutes.LogoutRoute
                ),
            ),
            onItemClick = {
                drawerItemClickListener?.invoke(it)
            }
        )
    }

}