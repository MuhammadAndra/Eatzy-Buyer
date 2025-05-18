package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class User(
    //contoh dataclass silahkan edit jika perlu
    @SerializedName("name")
    val username:String,
    val email:String,
    val id:Int=0
)
