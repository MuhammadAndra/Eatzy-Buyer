package com.example.eatzy_buyer.data.repository

import android.util.Log
import com.example.eatzy_buyer.data.model.Canteen
import com.example.eatzy_buyer.data.model.MenuCategory
import com.example.eatzy_buyer.data.network.RetrofitClient

class CanteenRepository {
     suspend fun getCanteens(token:String): List<Canteen> {
        return try {
            val response = RetrofitClient.canteenApi.getCanteens(token = "Bearer $token")
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList() // bisa juga lempar exception
            }
        } catch (e: Exception) {
            emptyList() // bisa juga lempar exception
        }
    }

    suspend fun getAllMenuCategoryByCanteen(id:Int):List<MenuCategory>{
        return try {
            val response = RetrofitClient.canteenApi.getAllMenuCategoryByCanteen(id = id)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList() // bisa juga lempar exception
            }
        } catch (e: Exception) {
            emptyList() // bisa juga lempar exception
        }
    }
}