package com.example.eatzy_buyer.data.model

data class Order (
    val id: Int,
    val buyerId: Int,
    val canteenId: Int,
    val status: OrderStatus,
    val orderTime: String,
    val finishedTime: String,
    val scheduleTime: String?,
    val estimationTime: Int,
    val totalPrice: Double,
    val orderItem: List<OrderItem>,
)