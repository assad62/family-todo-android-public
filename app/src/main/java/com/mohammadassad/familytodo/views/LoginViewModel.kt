package com.mohammadassad.familytodo.views

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.mohammadassad.familytodo.firebase.FirebaseMessagingModule
import com.mohammadassad.familytodo.model.LoginUserRequest
import com.mohammadassad.familytodo.remote.RemoteServiceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private  val sharedPreferences: SharedPreferences,
    private val remoteServiceManager: RemoteServiceManager,
    private  val fcm: FirebaseMessaging
): ViewModel(){

    val isLoading = mutableStateOf(value = false)

    fun checkIfJWTIsValid(onResult: (Boolean) -> Unit){
         isLoading.value = true
        try {
            val token = sharedPreferences.getString("access_token","")
            val defaultZoneId: ZoneId = ZoneId.systemDefault()
            val date: Date = Date.from(LocalDate.now().atStartOfDay(defaultZoneId).toInstant())
            val jwt = JWT.decode(token)
            isLoading.value = false
            if (jwt.expiresAt.before(date)) {
                onResult(false)
            }
            else{
                onResult(true)
            }

        }catch (e:Exception){
            isLoading.value = false
            onResult(false)
        }


    }

    fun initFirebaseMessaging(){
        fcm.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d("9910","token is "+token)
            sharedPreferences.edit().putString("fcm_token",token).apply()
        })
    }

    suspend fun login(email:String, password:String,  onResult: (Boolean) -> Unit){

        val loginUserRequest = LoginUserRequest(email,password)
        isLoading.value = true

        viewModelScope.launch {

            val response = remoteServiceManager.login(loginUserRequest);
            isLoading.value = false
            if (response.isSuccessful){
                val token = response.body()?.data?.token
                sharedPreferences.edit().putString("access_token", token).apply()
                onResult(true)
            }
            else{
                onResult(false)
            }

        }
     }




}