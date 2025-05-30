package com.esp32flower.presentation.screens.system_overivew_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SystemOverviewViewModel(

): ViewModel() {

    private val _state = MutableStateFlow(SystemOverviewState())
    val state = _state.asStateFlow()
}