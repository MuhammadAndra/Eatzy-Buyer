package com.example.eatzy_buyer.data.model

data class AddOnCategory(
    val id:Int,
    val name:String,
    val isSingleChoice:Boolean,
    val addOns:List<AddOn>
)
