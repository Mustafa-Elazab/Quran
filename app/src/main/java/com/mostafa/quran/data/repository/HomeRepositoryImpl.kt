package com.mostafa.quran.data.repository
import com.mostafa.quran.data.model.BaseResponse
import com.mostafa.quran.data.remote.ApiServices
import com.mostafa.quran.data.remote.response.NetworkResponse
import com.mostafa.quran.domain.repository.HomeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices,
    ) : HomeRepository {
        override suspend fun getPrayTime() = flow {
        emit(NetworkResponse.Loading)
        when (val get = apiServices.getPrayTime(


        )) {
            is NetworkResponse.Success -> emit(NetworkResponse.Success(get.body))
            is NetworkResponse.ApiError -> if (get.code == 500) emit(
                NetworkResponse.ApiError(
                    BaseResponse(code = 500, data = "Internal Server Error"), get.code
                )
            )
            else emit(NetworkResponse.ApiError(get.body, get.code))
            is NetworkResponse.NetworkError -> emit(NetworkResponse.NetworkError(get.error))
            is NetworkResponse.UnknownError -> emit(NetworkResponse.UnknownError(get.error))
            else -> emit(NetworkResponse.Loading)
        }
    }



}