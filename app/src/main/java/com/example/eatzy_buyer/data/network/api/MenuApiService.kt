package com.example.eatzy_buyer.data.network.api

import com.example.eatzy_buyer.data.model.Canteen
import com.example.eatzy_buyer.data.model.FavoriteResponse
import com.example.eatzy_buyer.data.model.Menu
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MenuApiService {
    @GET("/menus/{id}")
    suspend fun getMenuById(
        @Path("id") id:Int
    ): Response<Menu>

    @GET("/menus/search/{query}")
    suspend fun getMenuByQuery(
        @Path("query") query:String
    ): Response<List<Menu>>

    @POST("/menus/{id}/favorites")
    suspend fun createFavorite(
        @Header("authorization") token: String,
        @Path("id") id:Int
    ):Response<FavoriteResponse>
}