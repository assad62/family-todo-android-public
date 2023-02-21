package com.mohammadassad.familytodo.firebase

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseMessagingModule {

    @Singleton
    @Provides
    fun provideFirebaseMessage(): FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }
}