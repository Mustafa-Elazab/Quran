package com.example.quran.data.repository

import com.example.quran.data.local.AladhanDao
import com.example.quran.data.remote.ApiServices
import com.example.quran.model.aladhan.AladhanResponse
import com.example.quran.utils.NetworkBoundRepository
import com.example.quran.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


@ExperimentalCoroutinesApi
@ActivityRetainedScoped
class HomeRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val dao: AladhanDao
) {

    fun getPrayTime(): Flow<Resource<AladhanResponse>> {

        return object : NetworkBoundRepository<AladhanResponse, AladhanResponse>() {

            override suspend fun saveRemoteData(response: AladhanResponse) =
                dao.insertPrayTime(response)

            override fun fetchFromLocal(): Flow<AladhanResponse> =
                dao.getAllPrayTime()
            //  dao.getAllFoods()

            override suspend fun fetchFromRemote(): Response<AladhanResponse> =
                apiServices.getPrayTime()
        }.asFlow()

    }


}