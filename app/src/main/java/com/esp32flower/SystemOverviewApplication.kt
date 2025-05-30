package com.esp32flower

import android.app.Application
import com.esp32flower.core.initKoin
import org.koin.android.ext.koin.androidContext

class SystemOverviewApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SystemOverviewApplication)
        }
    }
}