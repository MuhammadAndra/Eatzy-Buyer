package com.example.eatzy_buyer.data.network.api

import com.example.eatzy_buyer.data.model.Cart
import com.example.eatzy_buyer.data.model.Confirmation
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface ConfirmApiService {

    @PATCH("confirmation/confirm/{order_id}")
    suspend fun confirmOrder(
        @Path("order_id") order_id: Int,
        @Query("time") time: String?,    // Kirim waktu pesan untuk nanti, nullable
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET("confirmation/{order_id}")
    suspend fun getOrderById(
        @Path("order_id") order_id: Int,
        @Header("Authorization") token: String
    ): Response<Confirmation>
}
