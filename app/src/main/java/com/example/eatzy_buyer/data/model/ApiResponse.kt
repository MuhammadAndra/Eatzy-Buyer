package com.example.eatzy_buyer.data.model

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null,
    val requiresVerification: Boolean = false
)