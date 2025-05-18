package com.example.eatzy_buyer.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eatzy_buyer.data.model.Canteen
import com.example.eatzy_buyer.data.model.User
import com.example.eatzy_buyer.data.repository.CanteenRepository
import com.example.eatzy_buyer.data.repository.TestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = CanteenRepository()
    private val _canteens = MutableStateFlow<List<Canteen>>(emptyList())
    val canteens: StateFlow<List<Canteen>> = _canteens

    fun fetchCanteens(token:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _canteens.value = repository.getCanteens(token = token)
        }
    }

}