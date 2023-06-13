package com.markvtls.diploma

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class DiplomApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}