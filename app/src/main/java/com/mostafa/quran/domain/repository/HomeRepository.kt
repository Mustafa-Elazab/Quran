package com.mostafa.quran.domain.repository
import com.mostafa.quran.data.model.BaseResponse
import com.mostafa.quran.data.remote.response.NetworkResponse
import kotlinx.coroutines.flow.Flow
import com.mostafa.quran.domain.model.aladhan.AladhanResponse


interface HomeRepository {

 suspend fun getPrayTime(): Flow<NetworkResponse<AladhanResponse, BaseResponse>>

}