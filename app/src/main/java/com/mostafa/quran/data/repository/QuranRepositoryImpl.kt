package com.mostafa.quran.data.repository

import com.mostafa.quran.data.remote.ApiServices
import com.mostafa.quran.data.remote.dto.ErrorResponse
import com.mostafa.quran.data.remote.dto.SurahAudioDTO
import com.mostafa.quran.data.remote.response.NetworkResponse
import com.mostafa.quran.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class QuranRepositoryImpl @Inject constructor(
    @Named("BaseUrl2") private val api: ApiServices
) : QuranRepository {
    override suspend fun getSurahAudio(surah_id: Int?): Flow<NetworkResponse<SurahAudioDTO, ErrorResponse>> =
        flow {
            emit(NetworkResponse.Loading)
            when (val get =
                api.getSurahAudio(
                    surah_id = surah_id
                )) {
                is NetworkResponse.ApiError -> if (get.code == 500) emit(
                    NetworkResponse.ApiError(
                        ErrorResponse(
                            code = 500,
                            data = "Internal Server Error",
                            status = "Internal Server Error"
                        ),
                        get.code
                    )
                ) else emit(NetworkResponse.ApiError(get.body, get.code))

                NetworkResponse.Loading -> emit(NetworkResponse.Loading)
                is NetworkResponse.NetworkError -> emit(NetworkResponse.NetworkError(get.error))
                is NetworkResponse.Success -> {
                    emit(NetworkResponse.Success(get.body))
                }

                is NetworkResponse.UnknownError -> emit(NetworkResponse.UnknownError(get.error))
            }
        }

}

