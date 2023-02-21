package com.mohammadassad.familytodo.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.mohammadassad.familytodo.views.composables.AppLoader
import com.mohammadassad.familytodo.views.composables.CustomDialog
import com.mohammadassad.familytodo.views.composables.TaskCard
import kotlinx.coroutines.launch


@Composable
fun MyTasksScreen(
    viewModel: MyTasksViewModel = hiltViewModel(),
    ) {
    val scaffoldState = rememberScaffoldState()
    val showAddTaskDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val latestLifecycleEvent = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            latestLifecycleEvent.value = event
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    if(viewModel.signalRDisconnected.value){
        viewModel.initSignalR()
    }

//    if (latestLifecycleEvent.value == Lifecycle.Event.ON_RESUME) {
//
//    }



    LaunchedEffect(key1 = "", block = {
        viewModel.loadUserTasks();
    })

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Color(0xFF6200EE),
                modifier = Modifier.offset(x = (-0).dp, y = (-20).dp),
                onClick = {
                    showAddTaskDialog.value = true
                          },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add FAB",
                    tint = Color.White,
                )
            }
        }
    ) {
        if (viewModel.getTaskList().isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(vertical = 5.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "\uD83D\uDC65  My Tasks",
                                style = MaterialTheme.typography.h5
                            )
                        }
                    }

                    items(viewModel.getTaskList()) { task ->
                        TaskCard(
                            task = task,
                            onCompleteClicked = {
                                coroutineScope.launch {
                                    viewModel.completeTask(task)
                                }

                            },
                            onDeleteClick = {
                                coroutineScope.launch {
                                    viewModel.deleteTask(task)
                                }

                            }, onUpdateTask = { updatedTask ->
                                coroutineScope.launch {
                                    viewModel.updateTask(updatedTask = updatedTask)
                                }

                            }
                        )
                    }
                }
                if (viewModel.isLoading.value){
                    AppLoader()
                }

            }

        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No Tasks Added",
                    fontSize = 40.sp,
                    color = Color.Black,
                )
                Text(
                    text = "Click on + to add a task",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Serif
                )
            }
        }
        if (showAddTaskDialog.value) {
            CustomDialog(
                openDialog = showAddTaskDialog,
                addTask = { title,task ->
                    coroutineScope.launch {
                        viewModel.addTask(title, task){
                            coroutineScope.launch {
                                viewModel.loadUserTasks()
                            }
                        }
                    }

                }

            )
        }
    }


}






