package com.example.eatzy_buyer.data.network.api

import com.example.eatzy_buyer.data.model.Canteen
import com.example.eatzy_buyer.data.model.MenuCategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CanteenApiService {
    @GET("/canteens")
    suspend fun getCanteens(
        @Header("authorization") token: String
    ): Response<List<Canteen>>

    @GET("canteens/{id}/menu-category")
    suspend fun getAllMenuCategoryByCanteen(
        @Path("id") id:Int
    ):Response<List<MenuCategory>>
}