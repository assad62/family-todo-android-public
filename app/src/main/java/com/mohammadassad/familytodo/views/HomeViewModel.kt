package com.mohammadassad.familytodo.views

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.mohammadassad.familytodo.remote.RemoteServiceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteServiceManager: RemoteServiceManager,
    private val sharedPreferences: SharedPreferences
): ViewModel(){

    fun clearToken(){

        sharedPreferences.edit().remove("access_token").apply()
    }

}