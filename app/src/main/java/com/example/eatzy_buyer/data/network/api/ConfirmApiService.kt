package com.example.eatzy_buyer.data.network.api

import com.example.eatzy_buyer.data.model.Cart
import com.example.eatzy_buyer.data.model.Confirmation
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Header
import retrofit2.http.Path

interface ConfirmApiService {

    @GET("order/confirmed")
    suspend fun getConfirmedOrder(
        @Header("Authorization") token: String
    ): Response<Confirmation> // <-- ini harus Response<>

    @POST("order/confirm")
    suspend fun confirmOrder(
        @Header("Authorization") token: String
    ): Response<Unit> // <-- harus Response juga!

    @GET("confirmation/confirmed/{order_id}")
    suspend fun getOrderById(
        @Path("order_id") orderId: Int,
        @Header("Authorization") token: String
    ): Response<Confirmation>
}

