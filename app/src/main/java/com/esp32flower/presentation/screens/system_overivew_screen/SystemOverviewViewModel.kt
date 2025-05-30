package com.esp32flower.presentation.screens.system_overivew_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SystemOverviewViewModel(

): ViewModel() {

    private val _state = MutableStateFlow(SystemOverviewState())
    val state = _state.asStateFlow()

    fun onAction(action: SystemOverviewAction) {
        when(action) {
            is SystemOverviewAction.OnChangeGraphClick -> {
                TODO()
            }
            is SystemOverviewAction.OnClickWaterSettings -> {
                _state.update {
                    it.copy(
                        tankSize = action.newCapacity
                    )
                }
            }
            is SystemOverviewAction.OnRefillTankClick -> {
                _state.update {
                    it.copy(
                        waterLevel = state.value.tankSize
                    )
                }
            }
            is SystemOverviewAction.OnRunPumpClick -> {
                _state.update {
                    it.copy(
                        isPumpOn = action.isPumpOn
                    )
                }
            }
            is SystemOverviewAction.OnSelectedSensorChange -> {
               _state.update {
                   it.copy(
                       selectedSensor = action.sensor
                   )
               }
            }
        }
    }
}