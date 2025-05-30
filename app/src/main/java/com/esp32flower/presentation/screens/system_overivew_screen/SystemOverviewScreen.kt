package com.esp32flower.presentation.screens.system_overivew_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esp32flower.presentation.theme.Esp32FlowerTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SystemOverviewScreenRoot(
    viewModel: SystemOverviewViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SystemOverviewScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemOverviewScreen(
    state: SystemOverviewState,
    onAction: (SystemOverviewAction) -> Unit

) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var newCapacity by rememberSaveable { mutableStateOf("") }


    AnimatedVisibility(showDialog) {
        AlertDialog(
            icon = {},
            title = {
                Text(text = "Change tank capacity")
            },
            text = {
                Column {
                    Text("Enter new capacity in ml\n")
                    TextField(
                        value = newCapacity,
                        onValueChange = { newCapacity = it },
                        singleLine = true,
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAction(SystemOverviewAction.OnClickWaterSettings(newCapacity.toInt()))
                        showDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            },
            onDismissRequest = {}
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(top = 24.dp, start = 30.dp, end = 30.dp)

    ) {
        AnimatedVisibility(state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(20.dp)
            )
        }
        Text(
            text = "Select sensor:",
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = state.selectedSensor.sensorName,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()

            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                shape = RoundedCornerShape(
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp
                )
            ) {
                SensorType.entries.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.sensorName) },
                        onClick = {
                            onAction(SystemOverviewAction.OnSelectedSensorChange(item))
                            expanded = false
                        }
                    )
                }
            }
        }
        Text(
            text = "Water level: ${state.tankInfo.waterLevel}/${state.tankInfo.tankSize} ml",
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        LinearProgressIndicator(
            progress = { state.tankInfo.waterLevel / state.tankInfo.tankSize.toFloat() },
            color = if (state.tankInfo.waterLevel < 100) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            gapSize = 0.dp,
            modifier = Modifier
                .padding(top = 10.dp)
                .height(11.dp)
                .fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Text( text = "Water at the next measurement: ")
            Switch(
                checked = state.tankInfo.isPumpOn,
                onCheckedChange = {
                    onAction(SystemOverviewAction.OnRunPumpClick(it))
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    showDialog = true
                }
            ) {
                Text( text = "Change tank capacity")
            }
            Button(
                onClick = {
                    onAction(SystemOverviewAction.OnRefillTankClick)
                }
            ) {
                Text( text = "Fill tank")
            }
        }
        Button(
            onClick = {
                onAction(SystemOverviewAction.OnRefreshClick)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh"
            )
            Text(
                text = "Refresh",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview
@Composable
fun MovieListScreenPreview() {
    Esp32FlowerTheme {
        SystemOverviewScreenRoot()
    }
}
