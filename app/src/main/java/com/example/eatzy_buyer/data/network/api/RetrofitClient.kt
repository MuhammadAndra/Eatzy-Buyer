package com.example.eatzy_buyer.data.network.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3002" // Updated from localhost to 10.0.2.2

    val instance: UserApiService by lazy {
        try {
            // Create logging interceptoradmin123
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Log request and response bodies
            }

            // Add logging to OkHttp client
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApiService::class.java)
        } catch (e: Exception) {
            Log.e("RetrofitClient", "Error initializing Retrofit: ${e.message}", e)
            throw e
        }
    }
}