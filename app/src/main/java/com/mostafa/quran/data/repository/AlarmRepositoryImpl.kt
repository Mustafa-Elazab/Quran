package com.mostafa.quran.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.mostafa.quran.data.utils.PreferenceConstants
import com.mostafa.quran.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AlarmRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    AlarmRepository {


    override fun getAlarmTimes(): Flow<List<Long>> {
        return dataStore.data.map { preferences ->
            val alarmTimesCount = preferences[PreferenceConstants.ALARM_TIMES_KEY] ?: 0
            (0 until alarmTimesCount).map { index ->
                preferences[longPreferencesKey("alarm_time_$index")] ?: 0L
            }
        }
    }

    override suspend fun setAlarmTimes(alarmTimes: List<Long>) {
        dataStore.edit { preferences ->
            preferences[PreferenceConstants.ALARM_TIMES_KEY] = alarmTimes.size
            alarmTimes.forEachIndexed { index, timeInMillis ->
                preferences[longPreferencesKey("alarm_time_$index")] = timeInMillis
            }
        }
    }
}

