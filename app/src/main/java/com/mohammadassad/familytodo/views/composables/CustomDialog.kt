package com.mohammadassad.familytodo.views.composables

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Title
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohammadassad.familytodo.model.UserTask
import com.mohammadassad.familytodo.views.MyTasksViewModel
import java.util.*

@Composable
fun CustomDialog(openDialog: MutableState<Boolean>, addTask: (String,String)->Unit) {
    val maxCharacters = 200
    val txtFieldError = remember { mutableStateOf("") }
    var title by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var task by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Dialog(onDismissRequest = { openDialog.value = false }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.height(400.dp)
        ) {
            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Add a task",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "",
                            tint = colorResource(R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { openDialog.value = false }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.holo_green_light else R.color.holo_red_dark)
                                ),

                                ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Title,
                                contentDescription = "",
                                tint = Color.Black,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        },
                        placeholder = { Text(text = "Enter title") },
                        value = title,
                        onValueChange = {
                            title = it
                        })

                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.holo_green_light else R.color.holo_red_dark)
                                ),
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "Enter Task") },
                        value = task,
                        onValueChange = {
                            if (it.text.length <= maxCharacters)
                                task = it
                        })


                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom) {
                            Button(
                                onClick = {
                                    if (task.text.isEmpty() || title.text.isEmpty()) {
                                        txtFieldError.value = "Field can not be empty"
                                        return@Button
                                    } else {
                                        addTask.invoke(title.text,task.text)
                                        openDialog.value = false
                                    }

                                },
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text(text = "Done")
                            }
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun CustomAlertDialog(openDialog: MutableState<Boolean>, onCancel: () -> Unit, onComplete:()->Unit) {

    Dialog(onDismissRequest = { openDialog.value = false }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Complete Task?",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Spacer(modifier = Modifier.height(20.dp))

                    Box() {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Red
                                ),
                                onClick = {
                                    onCancel()
                                    openDialog.value = false

                                },
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(100.dp)
                            ) {
                                Text(text = "No", style = TextStyle(color = Color.White))
                            }
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF4da956)
                                ),
                                shape = RoundedCornerShape(20.dp),
                                onClick = {
                                    onComplete()
                                    openDialog.value = false

                                },
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(100.dp)
                            ) {
                                Text(text = "Complete", style = TextStyle(color = Color.White))
                            }

                        }


                    }
                }
            }
        }
    }
}


@Composable
fun CustomAlertDialog(openDialog: MutableState<Boolean>, onDelete: () -> Unit, titleText:String) {

    Dialog(onDismissRequest = { openDialog.value = false }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = titleText,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            colors = buttonColors(Color.Red),
                            onClick = {
                                onDelete()
                                openDialog.value = false

                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Delete", style = TextStyle(color = Color.White))
                        }
                    }
                }
            }
        }
    }
}

