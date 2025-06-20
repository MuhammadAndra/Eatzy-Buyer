package com.example.eatzy_buyer.ui.screen.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.eatzy_buyer.data.model.Cart
import com.example.eatzy_buyer.data.network.RetrofitClient
import com.example.eatzy_buyer.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.eatzy_buyer.token
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {

    private val _cart = MutableStateFlow<List<Cart>>(emptyList())
    val cart: StateFlow<List<Cart>> = _cart

    private val repository = CartRepository(RetrofitClient.cartApi)

    fun fetchCartFromApi() {
        repository.fetchCart(
            token,
            onSuccess = { cart -> _cart.value = cart },
            onError = { error -> Log.e("CartViewModel", "Fetch failed: ${error.message}") }
        )
    }

    fun getTotalPrice(): Double {
        return _cart.value.sumOf { it.total_price }
    }
}