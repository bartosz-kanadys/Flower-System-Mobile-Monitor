package com.esp32flower.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.esp32flower.presentation.screens.system_overivew_screen.SystemOverviewScreenRoot
import com.esp32flower.presentation.theme.Esp32FlowerTheme

@Composable
@Preview
fun App() {
    Esp32FlowerTheme {
        SystemOverviewScreenRoot()
    }
}