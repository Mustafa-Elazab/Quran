package com.example.quran.model.hadith

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HadithResponseItem(
    val id: String,
    val name: String,
    val description: String,
    val hadith: String
) : Parcelable