package com.mohammadassad.familytodo.views

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mohammadassad.familytodo.model.LoginUserRequest
import com.mohammadassad.familytodo.model.RegisterUserRequest
import com.mohammadassad.familytodo.model.RegisterUserResponse
import com.mohammadassad.familytodo.remote.RemoteServiceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val remoteServiceManager: RemoteServiceManager,
    private  val sharedPreferences: SharedPreferences,
    ) : ViewModel() {

    val isLoading = mutableStateOf(false)

    suspend fun register(firstName:String, lastName:String, email:String, password:String, familyIdentifier:String, onResult: (Boolean) -> Unit){
        isLoading.value = true
        val deviceId = sharedPreferences.getString("fcm_token","")
        Log.d("9910","device id in register is"+deviceId)
        val newUser = RegisterUserRequest(firstName = firstName,lastName = lastName, email = email,password= password,familyIdentifier=familyIdentifier, DeviceId = deviceId)
        val registerResponse = remoteServiceManager.register(newUser)

        if(registerResponse.isSuccessful){
            val loginUser = LoginUserRequest(email = email, password= password)
            val response =  remoteServiceManager.login(loginUser)
            val token = response.body()?.data?.token
            sharedPreferences.edit().putString("access_token", token).apply()
            isLoading.value = false
            onResult(true)

        }
        else{
            isLoading.value = false
            onResult(false)
        }

      }
    }

