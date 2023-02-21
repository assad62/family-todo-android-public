package com.mohammadassad.familytodo.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class OKHttpClientModule {

    @Singleton
    @Provides
    fun okHttpClient():OkHttpClient{
        val okHttpClientBuilder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor{ chain :Interceptor.Chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            val request = requestBuilder.method(original.method, original.body).build()
            chain.proceed(request)
        }
        return okHttpClientBuilder
            .addInterceptor(interceptor)
            .connectTimeout(Duration.ofSeconds(60))
            .readTimeout(Duration.ofSeconds(60))
            .writeTimeout(Duration.ofSeconds(60))
            .build()
    }



}