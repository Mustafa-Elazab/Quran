package com.mostafa.quran.domain.usecase



import com.mostafa.quran.domain.repository.HomeRepository
import javax.inject.Inject

class GetPrayTimeUseCase @Inject constructor(private val repository: HomeRepository) {

    suspend fun getPrayTime() =
        repository.getPrayTime()





}