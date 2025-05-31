package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class Order (
    @SerializedName("order_id")
    val id: Int = 1,
    @SerializedName("buyer_id")
    val buyerId: Int = 1,
    @SerializedName("canteen_id")
    val canteenId: Int = 1,
    @SerializedName("order_status")
    val status: OrderStatus = OrderStatus.INCART,
    @SerializedName("order_time")
    val orderTime: String = "",
    @SerializedName("order_finished_time")
    val finishedTime: String = "",
    @SerializedName("schedule_time")
    val scheduleTime: String? = "",
    @SerializedName("estimation_time")
    val estimationTime: Int = 1,
    @SerializedName("total_price")
    val totalPrice: Double = 0.0,
    @SerializedName("order_items")
    val orderItem: List<OrderItem> = emptyList(),
)