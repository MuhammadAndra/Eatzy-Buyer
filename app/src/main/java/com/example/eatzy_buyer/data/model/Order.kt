package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class Order (
    @SerializedName("order_id")
    val id: Int = 0,

    @SerializedName("buyer_id")
    val buyerId: Int = 0,

    @SerializedName("canteen")
    val canteen: Canteen = Canteen(),

    @SerializedName("order_status")
    val status: OrderStatus = OrderStatus.INCART,

    @SerializedName("order_time")
    val orderTime: String = "",

    @SerializedName("order_finished_time")
    val finishedTime: String = "",

    @SerializedName("schedule_time")
    val scheduleTime: String? = null,

    @SerializedName("estimation_time")
    val estimationTime: Int = 0,

    @SerializedName("total_price")
    val totalPrice: Double = 0.00,

    @SerializedName("order_items")
    val orderItem: List<OrderItem> = emptyList(),
)