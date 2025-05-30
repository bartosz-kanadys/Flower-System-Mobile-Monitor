package com.esp32flower.core

import com.esp32flower.data.FirestoreRepository
import com.esp32flower.presentation.screens.system_overivew_screen.SystemOverviewViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { FirebaseFirestore.getInstance() }

    singleOf(::FirestoreRepository)

    viewModelOf(::SystemOverviewViewModel)

}