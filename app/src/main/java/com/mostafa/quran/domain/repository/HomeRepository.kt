package com.mostafa.quran.domain.repository

import com.mostafa.quran.data.remote.dto.AladhanResponseDTO
import com.mostafa.quran.data.remote.dto.ErrorResponse
import com.mostafa.quran.data.remote.response.NetworkResponse
import kotlinx.coroutines.flow.Flow


interface HomeRepository {

    suspend fun getPrayTime(
        latitude: Double?,
        longitude: Double?,
        month: Int?,
        year: Int?,
    ): Flow<NetworkResponse<AladhanResponseDTO, ErrorResponse>>





}