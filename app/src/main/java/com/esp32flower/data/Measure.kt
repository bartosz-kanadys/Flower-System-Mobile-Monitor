package com.esp32flower.data

import com.google.firebase.Timestamp

data class Measure(
    val airTemperature: Float,
    val airHumidity: Float,
    val soilHumidity: Float,
    val lightIntensity: Float,
    val time: Timestamp,
)
