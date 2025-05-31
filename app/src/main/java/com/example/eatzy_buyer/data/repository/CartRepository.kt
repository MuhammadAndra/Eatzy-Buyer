package com.example.eatzy_buyer.data.repository

import com.example.eatzy_buyer.data.model.Cart
import com.example.eatzy_buyer.data.network.api.CartApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRepository(private val apiService: CartApiService) {

    fun fetchCart(
        token: String,
        onSuccess: (List<Cart>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val authHeader = "Bearer $token"

        apiService.getCart(authHeader).enqueue(object : Callback<List<Cart>> {
            override fun onResponse(call: Call<List<Cart>>, response: Response<List<Cart>>) {
                if (response.isSuccessful) {
                    val cart = response.body()
                    if (!cart.isNullOrEmpty()) {
                        onSuccess(cart)
                    } else {
                        onError(Throwable("Cart kosong"))
                    }
                } else {
                    val errorMsg = "Gagal mengambil cart. Kode: ${response.code()} - ${response.message()}"
                    onError(Throwable(errorMsg))
                }
            }

            override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                onError(Throwable("Gagal koneksi: ${t.message}"))
            }
        })
    }
}
