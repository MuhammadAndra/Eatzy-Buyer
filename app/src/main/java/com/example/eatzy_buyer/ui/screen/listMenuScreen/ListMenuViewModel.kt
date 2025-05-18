package com.example.eatzy_buyer.ui.screen.listMenuScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eatzy_buyer.data.model.MenuCategory
import com.example.eatzy_buyer.data.repository.CanteenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListMenuViewModel : ViewModel() {
    private val repository = CanteenRepository()

    private val _menuCategories = MutableStateFlow<List<MenuCategory>>(emptyList())
    val menuCategories: StateFlow<List<MenuCategory>> = _menuCategories

    fun fetchMenuCategories(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _menuCategories.value = repository.getAllMenuCategoryByCanteen(id = id)
        }
    }
}