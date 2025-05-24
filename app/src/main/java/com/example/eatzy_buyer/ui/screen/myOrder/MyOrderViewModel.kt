package com.example.eatzy_buyer.ui.screen.test

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.eatzy_buyer.data.model.Order
import com.example.eatzy_buyer.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyOrderViewModel : ViewModel() {

    private val repository = OrderRepository()

    private val _order = MutableStateFlow<Order>(Order())
    val order: StateFlow<Order> = _order

    suspend fun fetchOrdersByIdResponse(token: String, orderId: Int) {
        val response = repository.getOrdersByIdResponse(token = token, orderId = orderId)

        if (response.isSuccessful) {
            _order.value = response.body() ?: Order()
            Log.d("RESPONKU: ",response.body().toString())
        }
    }

}