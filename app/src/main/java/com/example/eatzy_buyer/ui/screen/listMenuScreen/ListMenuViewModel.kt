package com.example.eatzy_buyer.ui.screen.listMenuScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eatzy_buyer.data.model.Canteen
import com.example.eatzy_buyer.data.model.FavoriteResponse
import com.example.eatzy_buyer.data.model.MenuCategory
import com.example.eatzy_buyer.data.model.Order
import com.example.eatzy_buyer.data.repository.CanteenRepository
import com.example.eatzy_buyer.data.repository.MenuRepository
import com.example.eatzy_buyer.data.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListMenuViewModel : ViewModel() {
    private val canteenRepository = CanteenRepository()
    private val orderRepository = OrderRepository()
    private val menuRepository = MenuRepository()


    private val _menuCategories =
        MutableStateFlow<List<MenuCategory>>(emptyList())
    val menuCategories: StateFlow<List<MenuCategory>> = _menuCategories

    private val _canteen = MutableStateFlow<Canteen?>(null)
    val canteen: StateFlow<Canteen?> = _canteen

    private val _order = MutableStateFlow<Order?>(null)
    val order: StateFlow<Order?> = _order

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    fun fetchMenuCategories(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _menuCategories.value =
                canteenRepository.getAllMenuCategoryByCanteen(id = id)
        }
    }

    fun getCanteenById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _canteen.value = canteenRepository.getCanteensById(id = id)
        }
    }

    fun getOrderByCanteenId(token: String, canteenId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _order.value = orderRepository.getOrderByCanteenId(
                token = token,
                canteenId = canteenId
            )
        }
    }

    fun deleteOrderItemByIds(
        token: String,
        orderItemIds: List<Int>,
        canteenId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.deleteOrderItemByIds(
                token = token,
                orderItemIds = orderItemIds
            )
            _order.value = orderRepository.getOrderByCanteenId(
                token = token,
                canteenId = canteenId
            )
        }
    }

    fun createFavorite(token: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = menuRepository.createFavorite(token = token, id = id)
            _toastMessage.postValue(response.message)
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}