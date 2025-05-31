package com.example.eatzy_buyer.common

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object OrderUpdateNotifier {
    private val _trigger = MutableStateFlow(false)
    val trigger: StateFlow<Boolean> = _trigger

    fun notifyUpdate() {
        _trigger.value = !_trigger.value // Flip value to force recomposition
        Log.d("Notifier", "Trigger flipped")
    }
}
