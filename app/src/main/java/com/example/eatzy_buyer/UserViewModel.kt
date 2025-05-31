// UserViewModel.kt
package com.example.eatzy_buyer

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.eatzy_buyer.data.repository.AuthState
import com.example.eatzy_buyer.data.repository.UserRepository
import com.example.eatzy_buyer.data.repository.UserState
import com.example.eatzy_buyer.navigation.navGraph.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    val registerState = repository.registerState
    val loginState = repository.loginState
    val verifyOtpState = repository.verifyOtpState
    val resendOtpState = repository.resendOtpState
    val userState: StateFlow<UserState> = repository.userState
    val forgotPasswordState = repository.forgotPasswordState
    val resetPasswordState = repository.resetPasswordState

    val userRepository: UserRepository
        get() = repository

    private val _navigationEvent = Channel<Any>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch { repository.register(name, email, password) }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
            if (loginState.value.success) {
                fetchUserDetails()
            }
        }
    }

    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch { repository.verifyOtp(email, otp) }
    }

    fun resendOtp(email: String) {
        viewModelScope.launch { repository.resendOtp(email) }
    }

    fun fetchUserDetails() {
        viewModelScope.launch { repository.fetchUserDetails() }
    }

    fun updateUser(name: String) {
        viewModelScope.launch {
            val userId = getUserIdFromToken()
            if (userId != null) {
                repository.updateUser(userId, name)
            }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch { repository.forgotPassword(email) }
    }

    fun resetPassword(email: String, otp: String, newPassword: String) {
        viewModelScope.launch { repository.resetPassword(email, otp, newPassword) }
    }

    fun isLoggedIn(): Boolean = repository.getToken() != null

    fun resetAuthStates() {
        viewModelScope.launch { repository.resetAuthStatesOnly() }
    }

    fun logout() {
        viewModelScope.launch {
            repository.resetState()
            _navigationEvent.send(Login)
        }
    }

    private suspend fun getUserIdFromToken(): Int? {
        return withContext(Dispatchers.IO) {
            val token = repository.getToken()
            if (token == null) {
                Log.e("UserViewModel", "No JWT token found")
                return@withContext null
            }
            try {
                val parts = token.split(".")
                if (parts.size != 3) {
                    Log.e("UserViewModel", "Invalid JWT format")
                    return@withContext null
                }
                val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
                val idMatch = """"id":\s*(\d+)""".toRegex().find(payload)
                idMatch?.groups?.get(1)?.value?.toInt()
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error decoding token: ${e.message}", e)
                null
            }
        }
    }
}

class UserViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(UserRepository(context.applicationContext)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}