package com.example.eatzy_buyer.ui.screen.confirmation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eatzy_buyer.data.model.Confirmation
import com.example.eatzy_buyer.data.repository.ConfirmationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime

class ConfirmationViewModel : ViewModel() {

    private val repository = ConfirmationRepository()

    private val _confirmation = MutableStateFlow<Confirmation?>(null)
    val confirmation: StateFlow<Confirmation?> = _confirmation

    private val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTMsImVtYWlsIjoiY2F0dGxleWExNzI0QGdtYWlsLmNvbSIsInJvbGUiOiJidXllciIsImlhdCI6MTc0ODgzMTk4MiwiZXhwIjoxNzY0MzgzOTgyfQ.QSD6IKDSNWT9jniEMOPLbh0x3j_UQ32F2rt5af4ey2g"

    fun fetchOrderById(orderId: Int) {
        viewModelScope.launch {
            val result = repository.getOrderById(orderId, token)
            result.onSuccess {
                _confirmation.value = it
                Log.d("ConfirmationVM", "Order fetched: $it")
            }.onFailure {
                Log.e("ConfirmationVM", "Failed to fetch order $orderId: ${it.message}")
            }
        }
    }

    fun confirmOrder(
        order_id: Int,
        pickedTime: LocalTime?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (_confirmation.value == null) {
            onError("Data pesanan belum tersedia.")
            return
        }

        viewModelScope.launch {
            val result = repository.confirmOrder(order_id, pickedTime, token)
            result.onSuccess {
                Log.d("ConfirmationVM", "Order confirmed successfully")
                onSuccess()
            }.onFailure {
                Log.e("ConfirmationVM", "Failed to confirm order $order_id: ${it.message}")
                onError("Gagal mengonfirmasi pesanan: ${it.message}")
            }
        }
    }
}
