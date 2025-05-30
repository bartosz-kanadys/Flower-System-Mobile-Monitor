package com.esp32flower.presentation.screens.system_overivew_screen

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esp32flower.data.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class SystemOverviewViewModel(
    private val firestoreRepository: FirestoreRepository
): ViewModel() {

    private val _state = MutableStateFlow(SystemOverviewState())
    val state = _state.asStateFlow()
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

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
                viewModelScope.launch {
                    firestoreRepository.updateTankCapacity(action.newCapacity)
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
                viewModelScope.launch {
                    firestoreRepository.updateWaterLevel(state.value.tankInfo.waterLevel)
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
                viewModelScope.launch {
                    firestoreRepository.updateRunPump(action.isPumpOn)
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

    fun calculateMeasure(selectedDay: String, selectedSensor: SensorType): List<Float> {
        return state.value.measures
            .sortedBy { it.time }
            .filter { dateFormatter.format(it.time.toDate()) == selectedDay }
            .map { when(selectedSensor) {
                SensorType.AIR_TEMP  -> it.airTemperature
                SensorType.AIR_HUMIDITY  -> it.airHumidity
                SensorType.SOIL_HUMIDITY -> (it.soilHumidity / 4095) * 100
                SensorType.LIGHT_INTENSITY -> (it.lightIntensity / 4095) * 100
            } }
    }

    fun calculateTime(selectedDay: String): List<String> {
        val timestampInSelectedDay = state.value.measures
            .sortedBy { it.time }
            .filter { dateFormatter.format(it.time.toDate()) == selectedDay }
            .map {
                it.time
            }

        val timeStrings: List<String> = timestampInSelectedDay.map { measure ->
            formatter.format(measure.toDate()) // <-- KONWERSJA Timestamp → Date
        }
        return timeStrings
    }
}