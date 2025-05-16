package com.example.eatzy_buyer.data.network

import com.example.eatzy_buyer.data.network.api.TestApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASEURL = "http://10.0.2.2:3002/"
//    private val tokenInterceptor = Interceptor { chain ->
//        val token =
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MywiZW1haWwiOiJhbmRyYWxtYUBleGFtcGxlLmNvbSIsInJvbGUiOiJjYW50ZWVuIiwiaWF0IjoxNzQ3MTE5MDY3LCJleHAiOjE3NDcxMjI2Njd9.3mSBxc0U3H2gQ8rDRKV7TmU7SiLqHcSOrd3sJXFu7FU"
//        val newRequest = chain.request().newBuilder()
//            .addHeader("Authorization", "Bearer $token")
//            .build()
//        chain.proceed(newRequest)
//    }
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(tokenInterceptor)
            .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val testApi: TestApiService by lazy { retrofit.create(TestApiService::class.java) }

}