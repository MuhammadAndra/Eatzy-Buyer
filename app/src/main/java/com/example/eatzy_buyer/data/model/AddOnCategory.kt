package com.example.eatzy_buyer.data.model

data class AddOnCategory(
    val id:Int,
    val canteenId:Int = 0,
    val name:String,
    val isMultipleChoice:Boolean,
    val addOns:List<AddOn>
)
