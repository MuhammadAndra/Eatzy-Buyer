package com.example.eatzy_buyer

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object AuthApiService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    // Fungsi registrasi pengguna
    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
        return try {
            val response: HttpResponse = client.post("http://10.0.2.2:3002/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(RegisterRequest(name, email, password))
            }
            response.body()
        } catch (e: Exception) {
            RegisterResponse(error = "Gagal terhubung ke server: ${e.message}")
        }
    }

    // Fungsi verifikasi OTP
    suspend fun verifyOtp(email: String, otp: String): OtpResponse {
        return try {
            val response: HttpResponse = client.post("http://10.0.2.2:3002/auth/verify-otp") {
                contentType(ContentType.Application.Json)
                setBody(OtpRequest(email, otp))
            }
            response.body()
        } catch (e: Exception) {
            OtpResponse(error = "Gagal verifikasi OTP: ${e.message}")
        }
    }

    // Fungsi untuk kirim ulang OTP
    suspend fun resendOtp(email: String): OtpResponse {
        return try {
            val response: HttpResponse = client.post("http://10.0.2.2:3002/auth/resend-otp") {
                contentType(ContentType.Application.Json)
                setBody(ResendOtpRequest(email))
            }
            response.body()
        } catch (e: Exception) {
            OtpResponse(error = "Gagal mengirim ulang OTP: ${e.message}")
        }
    }

    suspend fun loginUser(email: String, password: String): LoginResponse {
        return try {
            val response: HttpResponse = client.post("http://10.0.2.2:3002/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }
            response.body()
        } catch (e: Exception) {
            LoginResponse(error = "Gagal login: ${e.message}")
        }
    }
}

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class RegisterResponse(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val error: String? = null
)

@Serializable
data class OtpRequest(
    val email: String,
    val otp: String
)

@Serializable
data class ResendOtpRequest(
    val email: String
)

@Serializable
data class OtpResponse(
    val success: Boolean? = null,
    val message: String? = null,
    val error: String? = null
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String? = null,
    val userId: Int? = null,
    val error: String? = null
)
