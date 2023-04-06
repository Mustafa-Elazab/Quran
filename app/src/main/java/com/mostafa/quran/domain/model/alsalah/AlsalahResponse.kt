package com.mostafa.quran.domain.model.alsalah

data class AlsalahResponse(
    val chapters: List<Chapter>
)

data class Chapter(
    val content: String,
    val id: Int,
    val name: String
)