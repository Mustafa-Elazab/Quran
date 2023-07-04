package com.mostafa.quran.data.remote.dto


import com.google.gson.annotations.SerializedName

data class AudioFile(
    @SerializedName("audio_url")
    val audioUrl: String?,
    @SerializedName("chapter_id")
    val chapterId: Int?,
    @SerializedName("file_size")
    val fileSize: Double?,
    @SerializedName("format")
    val format: String?,
    @SerializedName("id")
    val id: Int?
)