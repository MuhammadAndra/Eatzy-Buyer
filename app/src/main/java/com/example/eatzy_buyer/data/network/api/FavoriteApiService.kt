package com.example.eatzy_buyer.data.network.api

import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.model.MenuFavorite
import com.example.eatzy_buyer.data.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FavoriteApiService {
    @GET("/favorites")
    suspend fun getFavoritesResponse(
        @Header("authorization") token: String
    ): Response<List<Menu>>

    @DELETE("/favorites/{menuId}")
    suspend fun deleteFavorite(
        @Header("authorization") token: String,
        @Path("menuId") menuId: Int
    ): Response<Unit>

    //api test ambil all user versi pak aryo
//    @GET("/users")
//    fun getUsers(@Header("authorization") token: String): Call<List<User>>
}