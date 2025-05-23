package com.example.eatzy_buyer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val menu_id: Int,               // dari order_items.menu_id / menus.menu_id
    val menu_name: String,          // dari menus.menu_name
    val quantity: Int,              // dari item_details / custom logic
    val addons: List<String> = emptyList(), // dari order_item_addons + addons.addon_name
    val menu_price: Double,         // dari menus.menu_price
    val note: String? = null,       // bisa dari item_details jika ada field catatan
    val menu_image: String          // dari menus.menu_image
)

@Serializable
data class Cart(
    val order_id: Int,              // dari orders.order_id
    val canteen_name: String,       // dari canteens.canteen_name
    val items: List<CartItem>,      // mapping dari order_items
    val total_price: Double         // dari orders.total_price
)

