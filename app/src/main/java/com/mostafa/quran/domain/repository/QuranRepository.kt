package com.mostafa.quran.domain.repository

import com.mostafa.quran.data.remote.dto.ErrorResponse
import com.mostafa.quran.data.remote.dto.SurahAudioDTO
import com.mostafa.quran.data.remote.response.NetworkResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path

interface QuranRepository {


    suspend fun getSurahAudio(
        @Path("surah_id") surah_id: Int?,
    ): Flow<NetworkResponse<SurahAudioDTO, ErrorResponse>>
}