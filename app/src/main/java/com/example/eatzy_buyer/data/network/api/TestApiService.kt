package com.example.eatzy_buyer.data.network.api

import com.example.eatzy_buyer.data.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface TestApiService {
    //api test ambil all user
    //ini pakai Thread
    @GET("/users")
    fun getUsers(): Call<List<User>>

    //ini pakai coroutine
//    @GET("/users")
//    suspend fun getUsersResponse(
//        @Header("authorization") token: String
//    ): Response<List<User>>
    @GET("/users")
    suspend fun getUsersResponse(
    ): Response<List<User>>
}