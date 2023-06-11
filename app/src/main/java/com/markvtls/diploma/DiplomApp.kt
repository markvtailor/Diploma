package com.markvtls.diploma

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class DiplomApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("2b4c7e5f-6c7f-43e8-8a54-10803941ca22")
    }

}