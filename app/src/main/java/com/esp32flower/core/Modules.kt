package com.esp32flower.core

import com.esp32flower.presentation.screens.system_overivew_screen.SystemOverviewViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {



    // Database setup


    // Repository


    // ViewModels
    viewModelOf(::SystemOverviewViewModel)

}