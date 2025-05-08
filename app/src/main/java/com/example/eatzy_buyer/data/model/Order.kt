package com.example.eatzy_buyer.data.model

data class Order (
    val id: Int,
    val buyerId: Int,
    val canteen: Canteen,
    val orderStatus: String,
    val orderTime: String,
    val orderFinishedTime: String,
    val scheduleTime: String,
    val estimationTime: Int,
    val totalPrice: Double,
    val orderItem: List<OrderItem> = emptyList(),
)