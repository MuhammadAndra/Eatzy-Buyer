package com.example.eatzy_buyer.ui.screen.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.eatzy_buyer.data.model.Cart
import com.example.eatzy_buyer.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel : ViewModel() {

    // State untuk menyimpan data cart dari API
    private val _carts = MutableStateFlow<List<Cart>>(emptyList())
    val carts: StateFlow<List<Cart>> = _carts

    // Fungsi untuk memuat data cart dari API
    fun fetchCartsFromApi() {
        RetrofitClient.testApi.getCart().enqueue(object : Callback<List<Cart>> {
            override fun onResponse(call: Call<List<Cart>>, response: Response<List<Cart>>) {
                if (response.isSuccessful) {
                    _carts.value = response.body() ?: emptyList()
                    Log.d("CartViewModel", "Loaded carts: ${response.body()}")
                } else {
                    Log.e("CartViewModel", "Failed to load carts: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                Log.e("CartViewModel", "Error loading carts: ${t.message}")
            }
        })
    }

    // Menghitung total semua cart
    fun getTotalPrice(): Double {
        return _carts.value.sumOf { it.total_price }
    }

    // Menghapus semua cart (lokal)
    fun clearCart() {
        _carts.value = emptyList()
    }

    // Menghapus satu cart berdasarkan order_id
    fun removeCart(cart: Cart) {
        _carts.value = _carts.value.filterNot { it.order_id == cart.order_id }
    }

    // Memperbarui data cart lokal
    fun updateCart(updatedCart: Cart) {
        _carts.value = _carts.value.map {
            if (it.order_id == updatedCart.order_id) updatedCart else it
        }
    }
}
