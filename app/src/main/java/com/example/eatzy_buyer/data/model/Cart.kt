package com.example.eatzy_buyer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val menu_id: Int,
    val menu_name: String,
    val quantity: Int,
    val addons: List<String> = emptyList(),
    val menu_price: Double,
    val note: String? = null,
    val menu_image: String
)

@Serializable
data class Cart(
    val order_id: Int,
    val canteen_name: String,
    val items: List<CartItem>,
    val total_price: Double
)

