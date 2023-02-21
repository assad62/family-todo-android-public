package com.mohammadassad.familytodo.views.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.mohammadassad.familytodo.Extensions.formatTo
import com.mohammadassad.familytodo.Extensions.toDate
import com.mohammadassad.familytodo.model.UserTask
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskCard(
    task: UserTask,
    onCompleteClicked:() -> Unit,
    onDeleteClick: () -> Unit,
    onUpdateTask: (UserTask) -> Unit
) {

    var maxCharacters = 200
    var updatedTaskText by remember {
        mutableStateOf(task.taskName)
    }
    val showAddTaskCompleteDialog = remember { mutableStateOf(false) }
    val showAddTaskDeleteDialog = remember { mutableStateOf(false) }
    val showEditTaskDialog = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),

        shape = RoundedCornerShape(10.dp),
        elevation = 15.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            task.createdDate?.let {
                it.toDate()?.let { it1 ->
                    Text(
                        fontSize = 12.sp,
                        text = it1.formatTo("dd MMM hh:mm"),
                        style = TextStyle(
                            fontWeight = FontWeight.Light, color = Color.Black
                        )
                    )
                }
            }
            task.title?.let {
                Text(
                    fontSize = 18.sp, text = it, fontWeight = FontWeight.SemiBold,
                    style = TextStyle(
                        color = Color(0xFF5a529b)
                    )
                )
            }
            task.taskName?.let {
                Text(
                    fontSize = 18.sp, text = it, fontWeight = FontWeight.Light,
                    style = TextStyle(
                        color = Color(0xFF5a529b)
                    )
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            task.createdBy?.let {
                Text(
                    fontSize = 12.sp, text = "Created by: $it", style = TextStyle(
                        fontWeight = FontWeight.Light, color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 18.dp))
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth(5f)
                    .padding(15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(imageVector = Icons.Default.Edit,
                    tint = Color.White,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .drawBehind {
                            drawCircle(
                                color = Color(0xFFd7518b),
                                radius = 50f
                            )
                        }
                        .clickable {
                            showEditTaskDialog.value = true
                        })

                Spacer(modifier = Modifier.width(20.dp))

                Icon(imageVector = Icons.Default.Delete,
                    tint = Color.White,
                    contentDescription = "",
                    modifier = Modifier
                        .drawBehind {
                            drawCircle(
                                color = Color(0xFF383aaf),
                                radius = 50f
                            )
                        }
                        .clickable {
                            showAddTaskDeleteDialog.value = true
                        })
                Spacer(modifier = Modifier.width(20.dp))
//                Icon(imageVector = Icons.Default.Share,
//                    tint = Color.White,
//                    contentDescription = "",
//                    modifier = Modifier
//                        .padding(end = 5.dp)
//                        .drawBehind {
//                            drawCircle(
//                                color = Color(0xFFf1c14a),
//                                radius = 50f
//                            )
//                        })
                Row(
                    modifier = Modifier.fillMaxWidth(5f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.size(width = 120.dp, height = 40.dp),
                        onClick = {
                            showAddTaskCompleteDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF4da956)
                        ), shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Complete", style = TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        )

                    }
                }
            }
        }
    }

    if (showAddTaskCompleteDialog.value){
        CustomAlertDialog(openDialog = showAddTaskCompleteDialog, onCancel = {
            showAddTaskCompleteDialog.value = false
        }, onComplete = {
            showAddTaskCompleteDialog.value =false
            onCompleteClicked()
        })
    }

    if (showAddTaskDeleteDialog.value) {
        CustomAlertDialog(openDialog = showAddTaskDeleteDialog, onDelete = {
            onDeleteClick()
        }, titleText = "Confirm delete task?")
    }
    if (showEditTaskDialog.value) {
        AlertDialog(onDismissRequest = { /*action on dismiss request*/ },

            title = {
                task.title?.let { Text(text = it) }
            }, text = {
                updatedTaskText?.let {
                    OutlinedTextField(
                        modifier = Modifier.height(150.dp),
                        value = it,
                        onValueChange = {
                            if (it.length <= maxCharacters)
                                updatedTaskText = it
                        }, label = { Text(text = "Task") })
                }
            }, confirmButton = {
                Button(onClick = {
                    val updatedTask = task.copy(taskName = updatedTaskText)
                    onUpdateTask(updatedTask)
                    showEditTaskDialog.value = false
                }) {
                    Text(text = "Save")
                }
            }, dismissButton = {
                Button(onClick = {
                    showEditTaskDialog.value = false
                }) {
                    Text(text = "Cancel")
                }
            })
    }
}