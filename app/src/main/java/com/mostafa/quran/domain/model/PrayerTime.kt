package com.mostafa.quran.domain.model

import androidx.annotation.Keep


data class PrayerTime(
    val id: Int,
    val name: String,
    val time: String,
    val timeInMillis: Long,
    val uriSound:Int
)