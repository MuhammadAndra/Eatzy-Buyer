package com.example.eatzy_buyer.data.network.api

import com.example.eatzy_buyer.data.model.Cart
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CartApiService {
    @GET("cart")
    fun getCart(
        @Header("Authorization") token: String
    ): Call<List<Cart>>
}

