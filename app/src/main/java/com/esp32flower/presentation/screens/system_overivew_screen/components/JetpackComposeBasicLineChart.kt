package com.esp32flower.presentation.screens.system_overivew_screen.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.esp32flower.presentation.screens.system_overivew_screen.SensorType
import com.esp32flower.presentation.screens.system_overivew_screen.SystemOverviewState
import com.esp32flower.presentation.screens.system_overivew_screen.SystemOverviewViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries

@Composable
fun JetpackComposeBasicLineChart(
    viewModel: SystemOverviewViewModel,
    state: SystemOverviewState,
    selectedDay: String,
    selectedSensor: SensorType,
    modifier: Modifier = Modifier,
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val measureInSelectedDay = viewModel.calculateMeasure(selectedDay, selectedSensor)

    val timeStrings = viewModel.calculateTime(selectedDay)
    if (measureInSelectedDay.isEmpty()) {
        Text(text = "No data for selected day")
    } else {
        LaunchedEffect(selectedDay, selectedSensor) {
            modelProducer.runTransaction {
                lineSeries {
                    series(measureInSelectedDay)
                }
            }
        }

        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(),

                // Oś Y – temperatura
                startAxis = VerticalAxis.rememberStart(
                    valueFormatter = { _, value, _ ->
                        "${value.toInt()}" + when(selectedSensor) {
                            SensorType.AIR_TEMP  -> "°C"
                            SensorType.AIR_HUMIDITY  -> "%"
                            SensorType.SOIL_HUMIDITY -> "%"
                            SensorType.LIGHT_INTENSITY -> "%"
                        }
                    }
                ),

                // Oś X – godziny
                bottomAxis = HorizontalAxis.rememberBottom(
                    valueFormatter = { _, value, _ ->
                        val index = value.toInt()
                        timeStrings.getOrNull(index) ?: "?"
                    }
                )
            ),
            modelProducer = modelProducer,
            modifier = modifier
        )
    }
}