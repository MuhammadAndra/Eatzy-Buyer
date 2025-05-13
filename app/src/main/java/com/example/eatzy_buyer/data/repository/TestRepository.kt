package com.example.eatzy_buyer.data.repository

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eatzy_buyer.data.model.User
import com.example.eatzy_buyer.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestRepository {

    fun getUsers(
        onSuccess: (List<User>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val call = RetrofitClient.testApi.getUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body() ?: emptyList())
                } else {
                    onError(Throwable("Response not successful: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                onError(t)
            }
        })
    }

    suspend fun getUsersSuspend(): List<User> {
        return try {
            val response = RetrofitClient.testApi.getUsersResponse()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList() // bisa juga lempar exception
            }
        } catch (e: Exception) {
            emptyList() // bisa juga lempar exception
        }
    }
}