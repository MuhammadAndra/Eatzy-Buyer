package com.example.eatzy_buyer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationItem(
    val menu_id: Int,
    val menu_name: String,
    val menu_price: Double,
    val note: String? = null,
    val menu_image: String,
    val addons: List<String> = emptyList()
)

@Serializable
data class Confirmation(
    val order_id: Int,
    val canteen_name: String,
    val total_price: Double,
    val schedule_time: String? = null, // Bisa null jika belum dijadwalkan
    val canteen_id: Int,
    val items: List<ConfirmationItem> = emptyList() // Tambahkan list item di sini
)
