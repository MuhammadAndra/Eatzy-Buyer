package com.example.eatzy_buyer.ui.screen.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eatzy_buyer.data.model.User
import com.example.eatzy_buyer.data.network.RetrofitClient
import com.example.eatzy_buyer.data.network.RetrofitClient.testApi
import com.example.eatzy_buyer.data.repository.TestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestApiViewModel : ViewModel() {
    //contoh kalo mau ngambil user
    private val repository = TestRepository()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    suspend fun fetchUsersResponse(token: String) {
        val response = repository.getUsersResponse(token = token)
        if (response.isSuccessful) {
            _users.value = response.body() ?: emptyList()
        }
    }

//    ini fungsi ambil data dari pak aryo
//    fun fetchUsers(token: String) {
//        repository.getUsers(
//            token = token,
//            onSuccess = { _users.value = it },
//            onError = { throwable -> }
//        )
//    }
}