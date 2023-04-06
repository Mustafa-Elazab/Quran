package com.mostafa.quran.domain.model.azkar
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AzkarResponseItem(
    val array: List<AzkarArray>,
    val audio: String,
    val category: String,
    val filename: String,
    val id: Int
) : Parcelable

@Parcelize
data class AzkarArray(
    val audio: String,
    val count: Int,
    val filename: String,
    val id: Int,
    val text: String
) : Parcelable

