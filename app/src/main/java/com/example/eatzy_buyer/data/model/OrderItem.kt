package com.example.eatzy_buyer.data.model

data class OrderItem(
    val id: Int,
    val orderId: Int,
    val menu: Menu,
    val details: String,
    val addons: List<Addon>,
)
