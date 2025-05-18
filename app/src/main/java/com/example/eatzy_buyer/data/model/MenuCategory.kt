package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class MenuCategory(
    @SerializedName("menu_category_id")
    val id:Int = 0,
    @SerializedName("menu_category_name")
    val name:String = "",
    @SerializedName("menus")
    val menus:List<Menu> = emptyList()
)
