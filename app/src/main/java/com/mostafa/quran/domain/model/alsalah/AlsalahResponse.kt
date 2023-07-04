package com.mostafa.quran.domain.model.alsalah

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class AlsalahResponse(
    val chapters: List<Chapter>
)

@Parcelize
data class Chapter(
    val content: String,
    val id: Int,
    val name: String
) : Parcelable