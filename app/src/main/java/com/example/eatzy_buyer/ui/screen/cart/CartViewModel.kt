package com.example.eatzy_buyer.ui.screen.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.eatzy_buyer.data.model.Cart
import com.example.eatzy_buyer.data.network.RetrofitClient
import com.example.eatzy_buyer.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {

    private val _cart = MutableStateFlow<List<Cart>>(emptyList())
    val cart: StateFlow<List<Cart>> = _cart

    // Ganti token dengan milikmu yang valid
    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NSwiZW1haWwiOiJqYWVAZ21haWwuY29tIiwicm9sZSI6ImJ1eWVyIiwiaWF0IjoxNzQ4NTAxMzkxLCJleHAiOjE3NjQwNTMzOTF9.u-wWG5HUz9fUOj2KhYDjm8SjjjCzdT9yeqqFW_ezzlo"
    private val repository = CartRepository(RetrofitClient.cartApi)

    fun fetchCartFromApi() {
        repository.fetchCart(token,
            onSuccess = { cart ->
                _cart.value = cart
                Log.d("CartViewModel", "Carts loaded: $cart")
            },
            onError = { error ->
                Log.e("CartViewModel", "Fetch failed: ${error.message ?: "Unknown error"}")
            }
        )
    }

    fun getTotalPrice(): Double {
        return _cart.value.sumOf { it.total_price }
    }

    fun clearCart() {
        _cart.value = emptyList()
    }

    fun removeCart(cart: Cart) {
        _cart.value = _cart.value.filterNot { it.order_id == cart.order_id }
    }

    fun updateCart(updatedCart: Cart) {
        _cart.value = _cart.value.map {
            if (it.order_id == updatedCart.order_id) updatedCart else it
        }
    }
}
