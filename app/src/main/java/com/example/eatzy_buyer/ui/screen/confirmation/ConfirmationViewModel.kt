package com.example.eatzy_buyer.ui.screen.confirmation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eatzy_buyer.data.model.Confirmation
import com.example.eatzy_buyer.data.repository.ConfirmationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConfirmationViewModel : ViewModel() {
    private val repository = ConfirmationRepository()

    private val _confirmation = MutableStateFlow<Confirmation?>(null)
    val confirmation: StateFlow<Confirmation?> = _confirmation

    private val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NSwiZW1haWwiOiJqYWVAZ21haWwuY29tIiwicm9sZSI6ImJ1eWVyIiwiaWF0IjoxNzQ4NTAxMzkxLCJleHAiOjE3NjQwNTMzOTF9.u-wWG5HUz9fUOj2KhYDjm8SjjjCzdT9yeqqFW_ezzlo"
    // ✅ Ambil order berdasarkan orderId
    fun fetchOrderById(order_id: Int) {
        viewModelScope.launch {
            val result = repository.getOrderById(order_id, token)
            result.onSuccess {
                _confirmation.value = it
                Log.d("ConfirmationVM", "Berhasil ambil data order_id $order_id: $it")
            }.onFailure {
                Log.e("ConfirmationVM", "Gagal ambil data order_id $order_id: ${it.message}")
            }
        }
    }

    fun fetchConfirmation() {
        viewModelScope.launch {
            val result = repository.getConfirmedOrder(token)
            result.onSuccess {
                _confirmation.value = it
                Log.d("ConfirmationVM", "Berhasil ambil data: $it")
            }.onFailure {
                Log.e("ConfirmationVM", "Gagal ambil data: ${it.message}")
            }
        }
    }

    fun confirmOrder(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = repository.confirmOrder(token)
            result.onSuccess {
                onSuccess()
            }.onFailure {
                onError(it.message ?: "Terjadi kesalahan")
            }
        }
    }
}