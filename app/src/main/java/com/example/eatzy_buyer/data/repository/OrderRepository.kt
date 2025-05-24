package com.example.eatzy_buyer.data.repository

import com.example.eatzy_buyer.data.model.Order
import com.example.eatzy_buyer.data.network.RetrofitClient
import retrofit2.Response

class OrderRepository {

    suspend fun getOrdersByBuyerResponse(token: String): Response<List<Order>> {
        return RetrofitClient.orderApi.getOrdersByBuyerResponse(token = "Bearer $token")
    }

    suspend fun getOrdersByIdResponse(token: String, orderId: Int): Response<Order> {
        return RetrofitClient.orderApi.getOrdersByIdResponse(token = "Bearer $token", orderId = orderId)
    }

}