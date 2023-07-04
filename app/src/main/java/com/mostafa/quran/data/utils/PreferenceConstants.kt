package com.mostafa.quran.data.utils

import androidx.datastore.preferences.core.intPreferencesKey

object PreferenceConstants {
    const val IS_VIBRATE = "vibrate"
    const val SAB7A_COUNT = "count"
    val ALARM_TIMES_KEY = intPreferencesKey("alarm_times_key")
}