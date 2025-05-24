package com.example.eatzy_buyer.data.network.api

import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.model.MenuFavorite
import com.example.eatzy_buyer.data.model.Order
import com.example.eatzy_buyer.data.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface OrderApiService {
    @GET("/orders")
    suspend fun getOrdersByBuyerResponse(
        @Header("authorization") token: String
    ): Response<List<Order>>

    @GET("/orders/{id}")
    suspend fun getOrdersByIdResponse(
        @Header("authorization") token: String,
        @Path("id") orderId: Int
    ): Response<Order>

}