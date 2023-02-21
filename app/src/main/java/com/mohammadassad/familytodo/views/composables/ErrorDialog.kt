package com.mohammadassad.familytodo.views.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import com.mohammadassad.familytodo.model.MenuItem


@Composable
fun ErrorDialog(title:String, text:String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = onDismiss)
            { Text(text = "OK") }
        },

        title = { Text(text =title) },
        text = { Text(text = text) }
    )
}