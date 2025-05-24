package com.example.eatzy_buyer.ui.screen.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.model.MenuFavorite
import com.example.eatzy_buyer.data.model.User
import com.example.eatzy_buyer.data.network.RetrofitClient
import com.example.eatzy_buyer.data.network.RetrofitClient.testApi
import com.example.eatzy_buyer.data.repository.FavoriteRepository
import com.example.eatzy_buyer.data.repository.TestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteViewModel : ViewModel() {
    //contoh kalo mau ngambil user
    private val repository = FavoriteRepository()

    private val _favorites = MutableStateFlow<List<Menu>>(emptyList())
    val favorites: StateFlow<List<Menu>> = _favorites

    suspend fun fetchFavoritesResponse(token: String) {
        val response = repository.getFavoritesResponse(token = token)
        if (response.isSuccessful) {
            _favorites.value = response.body() ?: emptyList()
            Log.d("RESPONKU: ",response.body().toString())
        }
    }

    suspend fun deleteFavorite(token: String, menuId: Int) {
        val response = repository.deleteFavorite(token = token, menuId = menuId)
        if (response.isSuccessful) {
            fetchFavoritesResponse(token = token)
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