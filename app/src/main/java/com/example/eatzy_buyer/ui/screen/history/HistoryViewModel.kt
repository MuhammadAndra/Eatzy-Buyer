package com.example.eatzy_buyer.ui.screen.test

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.eatzy_buyer.data.model.Order
import com.example.eatzy_buyer.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HistoryViewModel : ViewModel() {

    private val repository = OrderRepository()

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    suspend fun fetchOrdersByBuyerResponse(token: String) {
        val response = repository.getOrdersByBuyerResponse(token = token)
        if (response.isSuccessful) {
            _orders.value = response.body() ?: emptyList()
            Log.d("RESPONKU: ",response.body().toString())
        }
    }

//    ini fungsi ambil data dari pak aryo
//    fun fetchUsers(token: String) {
//        repository.getUsers(
//            token = token,
//            onSuccess = { _users.value = it },
//            onError = { throwable -> }
//        )
//    }
}