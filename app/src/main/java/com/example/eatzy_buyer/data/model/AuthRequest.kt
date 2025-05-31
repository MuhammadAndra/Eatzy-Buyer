package com.example.eatzy_buyer.data.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirm: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class VerifyOTPRequest(
    val email: String,
    val otp: String
)

data class ResendOTPRequest(
    val email: String
)

data class AuthResponse(
    val token: String,
    val user: User
)