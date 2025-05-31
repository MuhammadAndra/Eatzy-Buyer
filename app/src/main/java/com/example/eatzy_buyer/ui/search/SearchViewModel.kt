package com.example.eatzy_buyer.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eatzy_buyer.data.model.Canteen
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.repository.CanteenRepository
import com.example.eatzy_buyer.data.repository.MenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val repository = MenuRepository()
    private val _menus = MutableStateFlow<List<Menu>>(emptyList())
    val menus: StateFlow<List<Menu>> = _menus

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage


    fun getMenuByQuery(query:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _menus.value = repository.getMenuByQuery(query = query)
        }
    }

    fun createFavorite(token: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.createFavorite(token = token, id = id)
            _toastMessage.postValue(response.message)
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}