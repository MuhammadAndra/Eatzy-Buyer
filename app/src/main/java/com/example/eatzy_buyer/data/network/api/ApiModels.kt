package com.example.eatzy_buyer.data.network.api

data class RegisterRequest(val name: String, val email: String, val password: String, val role:String = "buyer")
data class RegisterResponse(val message: String?, val error: String?)
data class LoginRequest(val email: String, val password: String, val deviceToken: String)
data class LoginResponse(val token: String?, val error: String?)
data class VerifyOtpRequest(val email: String, val otp: String)
data class VerifyOtpResponse(val message: String?, val error: String?)
data class ResendOtpRequest(val email: String)
data class ResendOtpResponse(val message: String?, val error: String?)
data class UserResponse(val name: String, val email: String, val error: String?)
data class UpdateUserRequest(val name: String) // New
data class UpdateUserResponse(val user_id: Int, val name: String, val error: String?) // New
data class ForgotPasswordRequest(val email: String) // New
data class ForgotPasswordResponse(val message: String?, val error: String?) // New
data class ResetPasswordRequest(val email: String, val otp: String, val newPassword: String) // New
data class ResetPasswordResponse(val message: String?, val error: String?) // New