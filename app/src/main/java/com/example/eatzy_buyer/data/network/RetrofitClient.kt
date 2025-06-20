package com.example.eatzy_buyer.data.network

import com.example.eatzy_buyer.data.network.api.TestApiService
import com.example.eatzy_buyer.data.network.api.ConfirmApiService
import com.example.eatzy_buyer.data.network.api.CartApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASEURL = "http://192.168.101.81:3002/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val testApi: TestApiService by lazy {
        retrofit.create(TestApiService::class.java)
    }

    val confirmApi: ConfirmApiService by lazy {
        retrofit.create(ConfirmApiService::class.java)
    }

    val cartApi: CartApiService by lazy {
        retrofit.create(CartApiService::class.java)
    }
}