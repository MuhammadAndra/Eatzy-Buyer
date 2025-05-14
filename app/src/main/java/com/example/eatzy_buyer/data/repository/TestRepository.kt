package com.example.eatzy_buyer.data.repository

import android.net.http.HttpException
import android.util.Log
import com.example.eatzy_buyer.data.model.User
import com.example.eatzy_buyer.data.network.RetrofitClient
import com.example.eatzy_buyer.data.network.api.TestApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestRepository {

    suspend fun getUsersResponse(token: String): Response<List<User>> {
        return RetrofitClient.testApi.getUsersResponse(token = "Bearer $token")
    }
//    ini fungsi ambil data dari pak aryo
//    fun getUsers(
//        token: String,
//        onSuccess: (List<User>) -> Unit,
//        onError: (Throwable) -> Unit
//    ) {
//        val call = RetrofitClient.testApi.getUsers(token = "Bearer $token")
//        call.enqueue(object : Callback<List<User>> {
//            override fun onResponse(
//                call: Call<List<User>>,
//                response: Response<List<User>>
//            ) {
//                if (response.isSuccessful) {
//                    onSuccess(response.body() ?: emptyList())
//                } else {
//                    onError(Throwable("Response not successful: ${response.code()}"))
//                }
//            }
//
//            override fun onFailure(call: Call<List<User>>, t: Throwable) {
//                onError(t)
//            }
//        })
//    }
}