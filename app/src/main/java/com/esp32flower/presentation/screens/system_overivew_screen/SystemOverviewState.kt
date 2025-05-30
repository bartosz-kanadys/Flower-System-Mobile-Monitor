package com.esp32flower.presentation.screens.system_overivew_screen

import com.esp32flower.data.Measure
import com.esp32flower.data.Tank
import com.google.firebase.Timestamp


data class SystemOverviewState(
    val measures: List<Measure> = listOf<Measure>(Measure(0f, 0f, 0f, 0f, Timestamp.now())),
    val tankInfo: Tank = Tank(500, 1000, false),
    val isLoading: Boolean = false,
    val selectedSensor: SensorType = SensorType.AIR_TEMP,
)
