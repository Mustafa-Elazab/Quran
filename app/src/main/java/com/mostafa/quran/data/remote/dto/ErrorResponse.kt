package com.mostafa.quran.data.remote.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class ErrorResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: String?,
    @SerializedName("status")
    val status: String?
) : Serializable