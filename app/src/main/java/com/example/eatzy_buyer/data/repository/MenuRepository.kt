package com.example.eatzy_buyer.data.repository

import com.example.eatzy_buyer.data.model.FavoriteResponse
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.network.RetrofitClient


class MenuRepository {
    suspend fun getMenuById(id:Int): Menu? {
        return try {
            val response = RetrofitClient.menuApi.getMenuById(id = id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null// bisa juga lempar exception
            }
        } catch (e: Exception) {
            null // bisa juga lempar exception
        }
    }

    suspend fun getMenuByQuery(query:String):List<Menu>{
        return try {
            val response = RetrofitClient.menuApi.getMenuByQuery(query = query)
            if (response.isSuccessful){
                response.body() ?: emptyList()
            }else{
                emptyList()
            }
        }catch (e:Exception){
            emptyList()
        }
    }

    suspend fun createFavorite(token:String,id:Int):FavoriteResponse {
        return try {
            val response = RetrofitClient.menuApi.createFavorite(
                token = "Bearer $token",
                id = id
            )
            if (response.isSuccessful) {
                response.body() ?: FavoriteResponse("Empty response body", false)
            } else {
                FavoriteResponse("Unsuccessful response", false)
            }
        } catch (e: Exception) {
            FavoriteResponse(e.toString(),false)
        }
    }
}