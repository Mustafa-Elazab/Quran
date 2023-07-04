package com.mostafa.quran.domain.repository

import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAlarmTimes(): Flow<List<Long>>

    suspend fun setAlarmTimes(alarmTimes: List<Long>)
}