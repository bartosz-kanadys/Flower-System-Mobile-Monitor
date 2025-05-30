package com.esp32flower.presentation.screens.system_overivew_screen

sealed interface SystemOverviewAction {
    data class OnClickWaterSettings(val newCapacity: Int): SystemOverviewAction
    data class OnRunPumpClick(val isPumpOn: Boolean): SystemOverviewAction
    data class OnChangeGraphClick(val sensor: SensorType): SystemOverviewAction
    data object OnRefillTankClick: SystemOverviewAction
    data class OnSelectedSensorChange(val sensor: SensorType): SystemOverviewAction
    data object OnRefreshClick: SystemOverviewAction
}

enum class SensorType(val sensorName: String){
    AIR_TEMP("Air Temperature"),
    AIR_HUMIDITY("Air Humidity"),
    SOIL_HUMIDITY("Soil Humidity"),
    LIGHT_INTENSITY("Light Intensity")
}