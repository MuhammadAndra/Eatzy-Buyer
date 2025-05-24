package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

enum class OrderStatus {
    @SerializedName("in_cart")
    INCART,

    @SerializedName("waiting")
    WAITING,

    @SerializedName("processing")
    PROCESSING,

    @SerializedName("finished")
    FINISHED,

    @SerializedName("canceled")
    CANCELED
}