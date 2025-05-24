package com.example.eatzy_buyer.data.repository

import android.net.http.HttpException
import android.util.Log
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.model.MenuFavorite
import com.example.eatzy_buyer.data.model.User
import com.example.eatzy_buyer.data.network.RetrofitClient
import com.example.eatzy_buyer.data.network.api.TestApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteRepository {

    suspend fun getFavoritesResponse(token: String): Response<List<Menu>> {
        return RetrofitClient.favoriteApi.getFavoritesResponse(token = "Bearer $token")
    }

    suspend fun deleteFavorite(token: String, menuId: Int): Response<Unit> {
        return RetrofitClient.favoriteApi.deleteFavorite(token = "Bearer $token", menuId = menuId)
    }

}