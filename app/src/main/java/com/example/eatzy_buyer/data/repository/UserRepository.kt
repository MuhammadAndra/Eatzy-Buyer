// data/repository/UserRepository.kt
package com.example.eatzy_buyer.data.repository

import android.content.Context
import android.util.Log
import com.example.eatzy_buyer.data.network.api.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AuthState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val token: String? = null
)

data class UserState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)

data class User(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null
)

class UserRepository(private val context: Context) {
    private val apiService = RetrofitClient.instance // Assume RetrofitClient handles initialization errors
        ?: throw IllegalStateException("Failed to initialize RetrofitClient")

    private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private val _registerState = MutableStateFlow(AuthState())
    val registerState: StateFlow<AuthState> get() = _registerState.asStateFlow()

    private val _loginState = MutableStateFlow(AuthState())
    val loginState: StateFlow<AuthState> get() = _loginState.asStateFlow()

    private val _verifyOtpState = MutableStateFlow(AuthState())
    val verifyOtpState: StateFlow<AuthState> get() = _verifyOtpState.asStateFlow()

    private val _resendOtpState = MutableStateFlow(AuthState())
    val resendOtpState: StateFlow<AuthState> get() = _resendOtpState.asStateFlow()

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> get() = _userState.asStateFlow()

    private val _forgotPasswordState = MutableStateFlow(AuthState())
    val forgotPasswordState: StateFlow<AuthState> get() = _forgotPasswordState.asStateFlow()

    private val _resetPasswordState = MutableStateFlow(AuthState())
    val resetPasswordState: StateFlow<AuthState> get() = _resetPasswordState.asStateFlow()

    suspend fun register(name: String, email: String, password: String) {
        _registerState.value = _registerState.value.copy(isLoading = true)
        try {
            val response = apiService.register(RegisterRequest(name, email, password))
            if (response.isSuccessful && response.body()?.message != null) {
                _registerState.value = _registerState.value.copy(success = true, isLoading = false)
            } else {
                val error = response.body()?.error ?: response.message() ?: "Registration failed"
                _registerState.value = _registerState.value.copy(error = error, isLoading = false)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Register error: ${e.message}", e)
            _registerState.value = _registerState.value.copy(error = e.message ?: "Network error", isLoading = false)
        }
    }

    suspend fun login(email: String, password: String) {
        _loginState.value = _loginState.value.copy(isLoading = true)
        try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body()?.token != null) {
                val token = response.body()!!.token!!
                sharedPreferences.edit()
                    .putString("TOKEN_KEY", token)
                    .putString("email", email)
                    .apply()
                _loginState.value = _loginState.value.copy(success = true, token = token, isLoading = false)
            } else {
                val error = response.body()?.error ?: response.message() ?: "Login failed"
                _loginState.value = _loginState.value.copy(error = error, isLoading = false)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Login error: ${e.message}", e)
            _loginState.value = _loginState.value.copy(error = e.message ?: "Network error", isLoading = false)
        }
    }

    suspend fun verifyOtp(email: String, otp: String) {
        _verifyOtpState.value = _verifyOtpState.value.copy(isLoading = true)
        try {
            val response = apiService.verifyOtp(VerifyOtpRequest(email, otp))
            if (response.isSuccessful && response.body()?.message != null) {
                _verifyOtpState.value = _verifyOtpState.value.copy(success = true, isLoading = false)
            } else {
                val error = response.body()?.error ?: response.message() ?: "OTP verification failed"
                _verifyOtpState.value = _verifyOtpState.value.copy(error = error, isLoading = false)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Verify OTP error: ${e.message}", e)
            _verifyOtpState.value = _verifyOtpState.value.copy(error = e.message ?: "Network error", isLoading = false)
        }
    }

    suspend fun resendOtp(email: String) {
        _resendOtpState.value = _resendOtpState.value.copy(isLoading = true)
        try {
            val response = apiService.resendOtp(ResendOtpRequest(email))
            if (response.isSuccessful && response.body()?.message != null) {
                _resendOtpState.value = _resendOtpState.value.copy(success = true, isLoading = false)
            } else {
                val error = response.body()?.error ?: response.message() ?: "Resend OTP failed"
                _resendOtpState.value = _resendOtpState.value.copy(error = error, isLoading = false)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Resend OTP error: ${e.message}", e)
            _resendOtpState.value = _resendOtpState.value.copy(error = e.message ?: "Network error", isLoading = false)
        }
    }

    suspend fun fetchUserDetails() {
        _userState.value = _userState.value.copy(isLoading = true)
        val token = sharedPreferences.getString("TOKEN_KEY", null)
        if (token == null) {
            _userState.value = _userState.value.copy(isLoading = false, error = "No token found")
            return
        }
        try {
            val response = apiService.getUser("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                val userResponse = response.body()!!
                _userState.value = _userState.value.copy(
                    isLoading = false,
                    user = User(name = userResponse.name, email = userResponse.email)
                )
            } else {
                val error = if (response.code() == 401 || response.code() == 403) {
                    "Invalid session. Please log in again."
                } else {
                    response.body()?.error ?: response.message() ?: "Failed to fetch user details"
                }
                _userState.value = _userState.value.copy(isLoading = false, error = error)
                if (response.code() == 401 || response.code() == 403) {
                    sharedPreferences.edit().remove("TOKEN_KEY").apply()
                }
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Fetch user error: ${e.message}", e)
            _userState.value = _userState.value.copy(isLoading = false, error = e.message ?: "Network error")
        }
    }

    suspend fun updateUser(userId: Int, name: String) {
        _userState.value = _userState.value.copy(isLoading = true)
        val token = sharedPreferences.getString("TOKEN_KEY", null)
        if (token == null) {
            _userState.value = _userState.value.copy(isLoading = false, error = "No token found")
            return
        }
        try {
            val response = apiService.updateUser(userId, "Bearer $token", UpdateUserRequest(name))
            if (response.isSuccessful && response.body() != null) {
                val updatedUser = response.body()!!
                _userState.value = _userState.value.copy(
                    isLoading = false,
                    user = _userState.value.user?.copy(id = userId, name = updatedUser.name)
                )
            } else {
                val error = response.body()?.error ?: response.message() ?: "Failed to update user"
                _userState.value = _userState.value.copy(isLoading = false, error = error)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Update user error: ${e.message}", e)
            _userState.value = _userState.value.copy(isLoading = false, error = e.message ?: "Network error")
        }
    }

    suspend fun forgotPassword(email: String) {
        _forgotPasswordState.value = _forgotPasswordState.value.copy(isLoading = true)
        try {
            val response = apiService.forgotPassword(ForgotPasswordRequest(email))
            if (response.isSuccessful && response.body()?.message != null) {
                _forgotPasswordState.value = _forgotPasswordState.value.copy(success = true, isLoading = false)
            } else {
                val error = response.body()?.error ?: response.message() ?: "Failed to send reset OTP"
                _forgotPasswordState.value = _forgotPasswordState.value.copy(error = error, isLoading = false)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Forgot password error: ${e.message}", e)
            _forgotPasswordState.value = _forgotPasswordState.value.copy(error = e.message ?: "Network error", isLoading = false)
        }
    }

    suspend fun resetPassword(email: String, otp: String, newPassword: String) {
        _resetPasswordState.value = _resetPasswordState.value.copy(isLoading = true)
        try {
            val response = apiService.resetPassword(ResetPasswordRequest(email, otp, newPassword))
            if (response.isSuccessful && response.body()?.message != null) {
                _resetPasswordState.value = _resetPasswordState.value.copy(success = true, isLoading = false)
            } else {
                val error = response.body()?.error ?: response.message() ?: "Failed to reset password"
                _resetPasswordState.value = _resetPasswordState.value.copy(error = error, isLoading = false)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Reset password error: ${e.message}", e)
            _resetPasswordState.value = _resetPasswordState.value.copy(error = e.message ?: "Network error", isLoading = false)
        }
    }

    fun resetAuthStatesOnly() {
        _registerState.value = _registerState.value.copy(isLoading = false, success = false, error = null)
        _loginState.value = _loginState.value.copy(isLoading = false, success = false, error = null, token = null)
        _verifyOtpState.value = _verifyOtpState.value.copy(isLoading = false, success = false, error = null)
        _resendOtpState.value = _resendOtpState.value.copy(isLoading = false, success = false, error = null)
        _forgotPasswordState.value = _forgotPasswordState.value.copy(isLoading = false, success = false, error = null)
        _resetPasswordState.value = _resetPasswordState.value.copy(isLoading = false, success = false, error = null)
        Log.d("UserRepository", "Auth states reset (token and user details preserved)")
    }

    fun resetState() {
        _registerState.value = AuthState()
        _loginState.value = AuthState()
        _verifyOtpState.value = AuthState()
        _resendOtpState.value = AuthState()
        _userState.value = UserState()
        _forgotPasswordState.value = AuthState()
        _resetPasswordState.value = AuthState()
        sharedPreferences.edit().clear().apply()
        Log.d("UserRepository", "Full state reset and SharedPreferences cleared")
    }

    fun getToken(): String? = sharedPreferences.getString("TOKEN_KEY", null)
    fun getEmail(): String? = sharedPreferences.getString("email", null)
}