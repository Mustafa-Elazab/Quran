package com.mostafa.quran.domain.model

data class PrayerTime(
    val id: Int,
    val name: String,
    val time: String,
    val timeInMillis: Long,
    val uriSound:Int
)