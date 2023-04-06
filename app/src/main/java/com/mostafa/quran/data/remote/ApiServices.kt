package com.mostafa.quran.data.remote

import com.mostafa.quran.data.model.BaseResponse
import com.mostafa.quran.data.remote.response.NetworkResponse
import com.mostafa.quran.domain.model.aladhan.AladhanResponse

import retrofit2.http.GET

interface ApiServices {


    @GET("timingsByAddress?address=cairo")
    suspend fun getPrayTime(): NetworkResponse<AladhanResponse, BaseResponse>


}