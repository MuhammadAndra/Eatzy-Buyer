package com.example.eatzy_buyer.data.model

import androidx.room.Entity import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId: Int,
    val name: String,
    val role: String,
    val email: String,
    val password: String,
    val isVerified: Boolean,
    val createdAt: String,
    val updatedAt: String )