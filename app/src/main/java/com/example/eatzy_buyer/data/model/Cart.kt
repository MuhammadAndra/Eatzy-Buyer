package com.example.eatzy_buyer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val name: String,
    val quantity: Int,
    val addon: List<String> = emptyList(),
    val price: Double,
    val note: String? = null,
    val imageUrl: String
)

@Serializable
data class Cart(
    val id: Int,
    val kantinName: String,
    val items: List<CartItem>,
    val total: Double
)
