package com.mostafa.quran.domain.model.aladhan

import com.google.gson.annotations.SerializedName


data class AladhanResponse(

    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
) {
    companion object {
        val empty = AladhanResponse(
            code = null,
            data = null,
            status = null
        )
    }
}

data class Data(
    @SerializedName("timings")
    val timings: Timings?
)


data class Timings(
    @SerializedName("Asr")
    val asr: String?,
    @SerializedName("Dhuhr")
    val dhuhr: String?,
    @SerializedName("Fajr")
    val fajr: String?,
    @SerializedName("Firstthird")
    val firstthird: String?,
    @SerializedName("Imsak")
    val imsak: String?,
    @SerializedName("Isha")
    val isha: String?,
    @SerializedName("Lastthird")
    val lastthird: String?,
    @SerializedName("Maghrib")
    val maghrib: String?,
    @SerializedName("Midnight")
    val midnight: String?,
    @SerializedName("Sunrise")
    val sunrise: String?,
    @SerializedName("Sunset")
    val sunset: String?
)

