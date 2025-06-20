package com.example.eatzy_buyer.data.repository

import com.example.eatzy_buyer.data.model.Confirmation
import com.example.eatzy_buyer.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ConfirmationRepository {

    suspend fun confirmOrder(order_id: Int, pickedTime: LocalTime?, token: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val timeString = pickedTime?.let {
                    val now = LocalDate.now()
                    val fullDateTime = LocalDateTime.of(now, it)
                    fullDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                }

                val response = RetrofitClient.confirmApi.confirmOrder(order_id, timeString, "Bearer $token")
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