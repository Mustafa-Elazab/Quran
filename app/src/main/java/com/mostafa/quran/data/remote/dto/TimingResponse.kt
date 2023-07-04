package com.mostafa.quran.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TimingResponse(
    @SerializedName("Asr")
    val asr: String?,
    @SerializedName("Dhuhr")
    val dhuhr: String?,
    @SerializedName("Fajr")
    val fajr: String?,
    @SerializedName("Isha")
    val isha: String?,
    @SerializedName("Maghrib")
    val maghrib: String?,
    @SerializedName("Sunrise")
    val sunrise: String?,

)