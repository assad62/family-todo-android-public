package com.mohammadassad.familytodo.views.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun EditUpdateTextField(
    familyName: String,
    onTextChanged: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editingText by remember { mutableStateOf(familyName) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        if (isEditing) {
            OutlinedTextField(
                value = editingText,
                onValueChange = { editingText = it },
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    onTextChanged(editingText)
                    isEditing = false
                }
            ) {
                Icon(Icons.Default.Done, contentDescription = "Done")
            }
        } else {
            Text(
                text = familyName,
                modifier = Modifier.weight(1f), style = TextStyle(
                    fontWeight = FontWeight.Medium, fontSize = 20.sp
                )
            )
            IconButton(
                onClick = { isEditing = true }
            ) {
                Icon(Icons.Default.Create, contentDescription = "Edit")
            }
        }
    }
}