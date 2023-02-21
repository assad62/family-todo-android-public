package com.mohammadassad.familytodo.views

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.signalr.HubConnectionState
import com.mohammadassad.familytodo.SignalR.SignalRConfig
import com.mohammadassad.familytodo.model.AddTaskRequest
import com.mohammadassad.familytodo.model.PatchRequest
import com.mohammadassad.familytodo.model.UserTask
import com.mohammadassad.familytodo.remote.RemoteServiceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTasksViewModel @Inject constructor(
    private val remoteServiceManager: RemoteServiceManager,
    private  val signalRConfig: SignalRConfig,

) : ViewModel() {

    var  isLoading = mutableStateOf<Boolean>(value = false)
    private var _taskList = mutableStateListOf<UserTask>()
    public var signalRDisconnected = mutableStateOf<Boolean>(false)
    val pickedImage = mutableStateOf<Uri?>(null)


    init {
        initSignalR()
    }

    suspend fun loadUserTasks(){
        isLoading.value = true
        val response = remoteServiceManager.getUserTasks()
        _taskList.removeAll(elements = _taskList)
        if (response != null) {
            isLoading.value = false
            if(response.isSuccessful){
                response.body()?.let {
                    _taskList.addAll(it.data)
                };
            }

        }
    }

    fun initSignalR(){
        signalRConfig.initSignalR()
            .on(
            "TaskUpdate",
            { message: String ->
                Log.d(
                    "9910",
                    "New Message: $message"
                )
                viewModelScope.launch {
                    loadUserTasks();
                }

            },
            String::class.java)

       //refresh user tasks as soon as hub connected
        signalRConfig.initSignalR().connectionState?.let {
            when (it) {
                HubConnectionState.CONNECTED -> {
                    Log.d("9910","hub connected")
                    viewModelScope.launch {
                        loadUserTasks()
                    }
                }
                HubConnectionState.DISCONNECTED -> {
                    Log.d("9910","hub disconnected")
                }
                else -> {

                }
            }
        }
        signalRConfig.initSignalR().onClosed {
            signalRDisconnected.value = true
        }
    }


    fun getTaskList(): List<UserTask> {

        return _taskList
    }

    suspend fun deleteTask(taskItem: UserTask) {
        taskItem.id?.let { remoteServiceManager.
             deleteTask(it)
        }
    }

    suspend fun addTask(title:String,taskName:String,onResult: ()->Unit) {
        val addTaskRequest = AddTaskRequest(title = title, taskName = taskName)
        remoteServiceManager.addTask(addTaskRequest)
        onResult()
    }

    suspend fun updateTask(updatedTask: UserTask) {
        val completeTask = PatchRequest(
            op = "replace",
            path = "taskName",
            value = updatedTask.taskName
        )
        isLoading.value = true
        val response =
            updatedTask.id?.let {
                remoteServiceManager.updateTaskName(taskId = it, patchRequest = listOf(completeTask))

            }
        isLoading.value = false
    }

    suspend fun completeTask(taskItem: UserTask){
        val completeTask = PatchRequest(
            op = "replace",
            path = "taskStatus",
            value = "Done"
        )
        isLoading.value = true
        val response =
            taskItem.id?.let {
                remoteServiceManager.completeTask(taskId = it, patchRequest = listOf(completeTask))

            }
        isLoading.value = false

    }
}