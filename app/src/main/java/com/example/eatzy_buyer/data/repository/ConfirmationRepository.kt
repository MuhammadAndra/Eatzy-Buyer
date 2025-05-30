package com.example.eatzy_buyer.data.repository

import com.example.eatzy_buyer.data.model.Confirmation
import com.example.eatzy_buyer.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ConfirmationRepository {

    suspend fun getConfirmedOrder(token: String): Result<Confirmation> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.confirmApi.getConfirmedOrder("Bearer $token")
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Result.success(body)
                    } else {
                        Result.failure(Exception("Response body is null"))
                    }
                } else {
                    Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun confirmOrder(token: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.confirmApi.confirmOrder("Bearer $token")
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getOrderById(order_id: Int, token: String): Result<Confirmation> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.confirmApi.getOrderById(order_id, "Bearer $token")
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Result.success(body)
                    } else {
                        Result.failure(Exception("Response body is null"))
                    }
                } else {
                    Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
