package com.esp32flower.presentation.screens.system_overivew_screen

data class SystemOverviewState(
    val isLoading: Boolean = false,
    val airTemperature: Float = 0f,
    val airHumidity: Float = 0f,
    val soilHumidity: Float = 0f,
    val lightIntensity: Float = 0f,
    val waterLevel: Int = 500,
    val isPumpOn: Boolean = false,
    val selectedSensor: SensorType = SensorType.AIR_TEMP,
    val tankSize: Int = 1000
)