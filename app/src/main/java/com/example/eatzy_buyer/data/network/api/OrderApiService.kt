package com.example.eatzy_buyer.data.network.api

import com.example.eatzy_buyer.data.model.Order
import com.example.eatzy_buyer.data.model.OrderItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApiService {
    @POST("/orders")
    suspend fun createOrder(
        @Header("authorization") token: String,
        @Body order: Order
    ): Response<Unit>

    @GET("/canteens/{id}/order")
    suspend fun getOrderByCanteenId(
        @Header("authorization") token: String,
        @Path("id") canteenId:Int
    ): Response<Order>

    @POST("/orders/order-item")
    suspend fun createOrderItem(
        @Header("authorization") token: String,
        @Body orderItem: OrderItem
    ): Response<Unit>

    @POST("/orders/order-items")
    suspend fun createOrderItems(
        @Header("authorization") token: String,
        @Body orderItems: List<OrderItem>
    ): Response<Unit>

    @GET("/orders/order-item/{id}")
    suspend fun getOrderItemById(
        @Header("authorization")token: String,
        @Path("id") orderItemId:Int
    ): Response<OrderItem>

    @POST("/orders/get-order-items/")
    suspend fun getOrderItemsByIds(
        @Header("authorization")token: String,
        @Body ids:List<Int>
    ): Response<List<OrderItem>>

    @HTTP(method = "DELETE", path = "/orders/order-items/", hasBody = true)
    suspend fun deleteOrderItemByIds(
        @Header("authorization")token:String,
        @Body orderItemIds:List<Int>
    ):Response<Unit>
}
