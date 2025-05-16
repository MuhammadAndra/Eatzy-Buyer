package com.example.eatzy_buyer.ui.screen.cart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.eatzy_buyer.data.model.Cart
import com.example.eatzy_buyer.data.model.CartItem

class CartViewModel : ViewModel() {

    // Private mutable state
    private val _carts = MutableStateFlow<List<Cart>>(dummyCarts)
    // Public immutable state
    val carts: StateFlow<List<Cart>> = _carts

    fun getTotalPrice(): Double {
        return _carts.value.sumOf { it.total }
    }

    fun clearCart() {
        _carts.value = emptyList()
    }

    fun removeCart(cart: Cart) {
        _carts.value = _carts.value.filterNot { it.id == cart.id }
    }

    fun updateCart(updatedCart: Cart) {
        _carts.value = _carts.value.map {
            if (it.id == updatedCart.id) updatedCart else it
        }
    }

    companion object {
        // Dummy Data
        val dummyCarts = listOf(
            Cart(
                id = 1,
                kantinName = "Kantin 1",
                items = listOf(
                    CartItem("Menu 3", 1, listOf("Sambal Bawang", "Dibungkus"), 12000.0, imageUrl = "https://i0.wp.com/i.gojekapi.com/darkroom/gofood-indonesia/v2/images/uploads/eb33f8da-5098-4153-9e99-8feb74c9d072_Go-Biz_20231128_161650.jpeg")
                ),
                total = 12000.0
            ),
            Cart(
                id = 2,
                kantinName = "Kantin 3",
                items = listOf(
                    CartItem("Menu 5", 1, listOf(), 13000.0, "Pedas", imageUrl = "https://i0.wp.com/i.gojekapi.com/darkroom/gofood-indonesia/v2/images/uploads/eb33f8da-5098-4153-9e99-8feb74c9d072_Go-Biz_20231128_161650.jpeg")
                ),
                total = 13000.0
            ),
            Cart(
                id = 3,
                kantinName = "Kantin 2",
                items = listOf(
                    CartItem("Menu 3", 1, listOf(), 11000.0, "", imageUrl = "https://i0.wp.com/i.gojekapi.com/darkroom/gofood-indonesia/v2/images/uploads/eb33f8da-5098-4153-9e99-8feb74c9d072_Go-Biz_20231128_161650.jpeg"),
                    CartItem("Menu 1", 2, listOf("Bawang"), 11000.0, "Pedas dan Sedang", imageUrl = "https://i0.wp.com/i.gojekapi.com/darkroom/gofood-indonesia/v2/images/uploads/eb33f8da-5098-4153-9e99-8feb74c9d072_Go-Biz_20231128_161650.jpeg")
                ),
                total = 35000.0
            )
        )
    }
}
