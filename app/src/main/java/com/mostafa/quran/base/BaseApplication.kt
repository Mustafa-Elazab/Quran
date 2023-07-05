package com.mostafa.quran.base

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {




    @Inject
    lateinit var crashlytics: FirebaseCrashlytics

    override fun onCreate() {
        super.onCreate()
        setupCrashlytics()
    }

    private fun setupCrashlytics() {
        crashlytics.setCrashlyticsCollectionEnabled(true)
    }


}