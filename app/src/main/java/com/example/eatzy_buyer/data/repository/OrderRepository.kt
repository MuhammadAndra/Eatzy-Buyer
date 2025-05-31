package com.example.eatzy_buyer.data.repository

import com.example.eatzy_buyer.data.model.Order
import com.example.eatzy_buyer.data.model.OrderItem
import com.example.eatzy_buyer.data.network.RetrofitClient

class OrderRepository {
    suspend fun createOrder(token: String, order: Order): Unit? {
        return try {
            val response = RetrofitClient.orderApi.createOrder(
                token = "Bearer $token",
                order = order
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                null// bisa juga lempar exception
            }
        } catch (e: Exception) {
            null // bisa juga lempar exception
        }
    }

    suspend fun getOrderByCanteenId(token: String, canteenId: Int): Order? {
        return try {
            val response = RetrofitClient.orderApi.getOrderByCanteenId(
                token = "Bearer $token",
                canteenId = canteenId
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                null// bisa juga lempar exception
            }
        } catch (e: Exception) {
            null // bisa juga lempar exception
        }
    }

    suspend fun createOrderItem(token: String, orderItem: OrderItem):Unit?{
        return try {
            val response = RetrofitClient.orderApi.createOrderItem(
                token = "Bearer $token",
                orderItem = orderItem
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                null// bisa juga lempar exception
            }
        } catch (e: Exception) {
            null // bisa juga lempar exception
        }
    }

    suspend fun createOrderItems(token: String, orderItems: List<OrderItem>):Unit?{
        return try {
            val response = RetrofitClient.orderApi.createOrderItems(
                token = "Bearer $token",
                orderItems = orderItems
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                null// bisa juga lempar exception
            }
        } catch (e: Exception) {
            null // bisa juga lempar exception
        }
    }

    suspend fun getOrderItemById(token: String,orderItemId:Int):OrderItem?{
        return try {
            val response = RetrofitClient.orderApi.getOrderItemById(
                token = "Bearer $token",
                orderItemId = orderItemId
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                null// bisa juga lempar exception
            }
        } catch (e: Exception) {
            null // bisa juga lempar exception
        }
    }

    suspend fun getOrderItemsByIds(token: String, ids:List<Int>): List<OrderItem> {
        return try {
            val response =
                RetrofitClient.orderApi.getOrderItemsByIds(token = "Bearer $token",ids = ids)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList() // bisa juga lempar exception
            }
        } catch (e: Exception) {
            emptyList() // bisa juga lempar exception
        }
    }

    suspend fun deleteOrderItemByIds(token: String, orderItemIds:List<Int>):Unit?{
        return try {
            val response = RetrofitClient.orderApi.deleteOrderItemByIds(token = "Bearer $token", orderItemIds = orderItemIds)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}