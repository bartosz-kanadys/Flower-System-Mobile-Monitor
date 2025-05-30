package com.esp32flower.presentation.screens.system_overivew_screen

sealed interface SystemOverviewAction {
    data object OnClickWaterSettings: SystemOverviewAction
    data object OnRunPumpClick: SystemOverviewAction
    data class OnChangeGraphClick(val sensor: SensorType): SystemOverviewAction
    data object OnRefillTankClick: SystemOverviewAction
}

enum class SensorType{
    AIR_TEMP, AIR_HUMIDITY, SOIL_HUMIDITY, LIGHT_INTENSITY
}