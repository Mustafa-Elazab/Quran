package com.example.quran.data.remote


import com.example.quran.model.aladhan.AladhanResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    companion object {
        const val BASE_URL =
            "https://api.aladhan.com/v1/"
    }


    @GET("timingsByAddress?address=cairo")
    suspend fun getPrayTime(): Response<AladhanResponse>


}