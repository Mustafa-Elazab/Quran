package com.mostafa.quran.ui.cycles.home.sab7a.viewmodel

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Sab7aViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val vibrator: Vibrator
) :
    ViewModel() {

    private val _vibratorStatus = MutableStateFlow(false)
    val vibratorStatus get() = _vibratorStatus.asStateFlow()

    private val _counter = MutableStateFlow(0)
    val counter get() = _counter.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.data.firstOrNull()?.let { preferences ->
                _vibratorStatus.emit(preferences[VIBRATION] ?: false)
            }
        }
    }

    fun toggleVibrationStatus() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                val currentStatus = preferences[VIBRATION] ?: true
                preferences[VIBRATION] = !currentStatus
            }
        }
    }

    fun incrementCounter() {
        val currentValue = _counter.value
        _counter.value = currentValue + 1

        val shouldVibrate = _vibratorStatus.asLiveData()
        if (shouldVibrate.value == true) {
            vibrateDevice()
        }
    }

    fun resetCounter() {
        _counter.value = 0
    }

    private fun vibrateDevice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect =
                VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(200)
        }
    }

    companion object {
        val VIBRATION = booleanPreferencesKey("vibratorStatus")
    }
}
