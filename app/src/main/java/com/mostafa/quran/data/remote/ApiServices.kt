package com.mostafa.quran.data.remote

import com.mostafa.quran.data.remote.dto.AladhanResponseDTO
import com.mostafa.quran.data.remote.dto.ErrorResponse
import com.mostafa.quran.data.remote.dto.SurahAudioDTO
import com.mostafa.quran.data.remote.response.NetworkResponse
import com.mostafa.quran.domain.model.aladhan.AladhanResponse

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {



    @GET("calendar/{year}/{month}")
    suspend fun getPrayTime(
        @Path("year") year: Int? = null,
        @Path("month") month: Int? = null,
        @Query("latitude") latitude: Double? = null,
        @Query("longitude") longitude: Double? = null,
        @Query("method") method: Int = 4
    ): NetworkResponse<AladhanResponseDTO, ErrorResponse>



    @GET("chapter_recitations/1/{surah_id}")
    suspend fun getSurahAudio(
        @Path("surah_id") surah_id: Int? = 1,
    ): NetworkResponse<SurahAudioDTO, ErrorResponse>

}