package com.mostafa.quran.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChapterX(
    val Descent: String,
    val English_Name: String,
    val Number: Int,
    val Number_Letters: Int,
    val Number_Verses: Int,
    val Number_Words: Int,
    val content: String,
    val name: String
) : Parcelable
