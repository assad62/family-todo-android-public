package com.mohammadassad.familytodo.remote

import com.mohammadassad.familytodo.Constants
import com.mohammadassad.familytodo.services.FamilyTodoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ApiModule{
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient):Retrofit{
       return Retrofit.Builder()
           .baseUrl("${Constants.BASE_URL}/api/")
        //.baseUrl("http://10.0.2.2:5000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    }

    @Provides
    @Singleton
    fun familyTodoService(retrofit: Retrofit):FamilyTodoService{
        return retrofit.create(FamilyTodoService::class.java)
    }
}