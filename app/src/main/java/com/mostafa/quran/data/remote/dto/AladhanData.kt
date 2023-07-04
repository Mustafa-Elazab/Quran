package com.mostafa.quran.data.remote.dto


import com.google.gson.annotations.SerializedName


data class AladhanData(
    @SerializedName("timings")
    val timings: TimingResponse?
)