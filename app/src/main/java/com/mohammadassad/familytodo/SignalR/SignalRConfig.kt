package com.mohammadassad.familytodo.SignalR

import android.content.SharedPreferences
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.mohammadassad.familytodo.Constants
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignalRConfig @Inject constructor(
      private val sh:SharedPreferences
) {

    fun initSignalR():HubConnection {
        val token = sh.getString("access_token", "")
        val hubConnection = HubConnectionBuilder
            .create("${Constants.BASE_URL}/notificationHub")
            //.create("http://10.0.2.2:5000/notificationHub")
            .withAccessTokenProvider(Single.defer { Single.just(token) })
            .build()
         hubConnection
             .start()
             .blockingAwait()

         return  hubConnection
    }

}