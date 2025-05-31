package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class AddOnCategory(
    @SerializedName("addon_category_id")
    val id: Int = 1,
    @SerializedName("addon_category_name")
    val name: String = "",
    @SerializedName("is_multiple_choice")
    val isMultipleChoice: Boolean = true,
    @SerializedName("addon")
    val addOns: List<AddOn> = emptyList(),
    val canteenId: Int = 1
)
