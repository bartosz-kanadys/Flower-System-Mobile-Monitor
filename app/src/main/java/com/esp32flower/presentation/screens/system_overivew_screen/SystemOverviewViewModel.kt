package com.esp32flower.presentation.screens.system_overivew_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esp32flower.data.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SystemOverviewViewModel(
    private val firestoreRepository: FirestoreRepository
): ViewModel() {

    private val _state = MutableStateFlow(SystemOverviewState())
    val state = _state.asStateFlow()

    init {
       loadDataFromFirestore()
    }

    fun onAction(action: SystemOverviewAction) {
        when(action) {
            is SystemOverviewAction.OnChangeGraphClick -> {
                TODO()
            }
            is SystemOverviewAction.OnClickWaterSettings -> {
                _state.update {
                    it.copy(
                        tankInfo = it.tankInfo.copy(
                            tankSize = action.newCapacity
                        )
                    )
                }
            }
            is SystemOverviewAction.OnRefillTankClick -> {
                _state.update {
                    it.copy(
                        tankInfo = it.tankInfo.copy(
                            waterLevel = it.tankInfo.tankSize
                        )
                    )
                }
            }
            is SystemOverviewAction.OnRunPumpClick -> {
                _state.update {
                    it.copy(
                        tankInfo = it.tankInfo.copy(
                            isPumpOn = action.isPumpOn
                        )
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
            is SystemOverviewAction.OnRefreshClick -> {
                loadDataFromFirestore()
            }
        }
    }

    private fun loadDataFromFirestore() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) } // ustaw true przed startem
            try {
                val tank = firestoreRepository.getTankInfo()
                val data  = firestoreRepository.getMeasures()
                _state.update {
                    it.copy(
                        measures = data,
                        tankInfo = tank
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            finally {
                _state.update { it.copy(isLoading = false) } // ustaw false po zakończeniu
            }
        }
    }
}