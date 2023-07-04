package com.mostafa.quran.data.remote.dto


import com.google.gson.annotations.SerializedName

data class SurahAudioDTO(
    @SerializedName("audio_file")
    val audioFile: AudioFile?
)