package com.ttw.weather

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

    }
}