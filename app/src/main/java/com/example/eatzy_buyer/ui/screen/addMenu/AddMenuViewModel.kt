package com.example.eatzy_buyer.ui.screen.addMenu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eatzy_buyer.data.model.Canteen
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.model.MenuCategory
import com.example.eatzy_buyer.data.model.Order
import com.example.eatzy_buyer.data.model.OrderItem

import com.example.eatzy_buyer.data.repository.MenuRepository
import com.example.eatzy_buyer.data.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddMenuViewModel : ViewModel() {
    private val menuRepository = MenuRepository()
    private val orderRepository = OrderRepository()

    private val _menu = MutableStateFlow<Menu?>(null)
    val menu: StateFlow<Menu?> = _menu

    private val _orderItem = MutableStateFlow<OrderItem?>(null)
    val orderItem:StateFlow<OrderItem?> = _orderItem

    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val orderItems: StateFlow<List<OrderItem>> = _orderItems

//    val orderItems: StateFlow<List<OrderItem>> = emptyList()


    fun getMenuById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _menu.value = menuRepository.getMenuById(id = id)
        }
    }

    fun createOrder(token: String, order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.createOrder(token = token, order = order)
        }
    }

    fun createOrderItem(token: String, orderItem: OrderItem) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.createOrderItem(token = token, orderItem = orderItem)
        }
    }

    fun createOrderItems(token: String, orderItems: List<OrderItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.createOrderItems(token = token, orderItems = orderItems)
        }
    }

    fun getOrderItemById(token: String, orderItemId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _orderItem.value = orderRepository.getOrderItemById(token = token, orderItemId = orderItemId)
        }
    }

    fun getOrderItemsByIds(token: String, ids:List<Int>){
        viewModelScope.launch(Dispatchers.IO) {
            _orderItems.value = orderRepository.getOrderItemsByIds(token = token, ids = ids)
        }
    }
}
