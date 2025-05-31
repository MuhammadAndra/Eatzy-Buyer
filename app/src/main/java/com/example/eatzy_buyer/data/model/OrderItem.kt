package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class OrderItem(
    @SerializedName("order_item_id")
    val id: Int = 1,
    @SerializedName("order_id")
    val orderId: Int = 1,
    @SerializedName("menu_id")
    val menuId: Int = 1,
    @SerializedName("item_details")
    val details: String?,
    @SerializedName("menu_name")
    val menuName: String = "",
    @SerializedName("menu_image")
    val imageUrl: String = "",
    val menu: Menu = Menu(),
    val orderItemAddons:List<OrderItemAddon> = emptyList(),
    @SerializedName("order_item_addons")
    val addOns:List<AddOn> = emptyList()
)
