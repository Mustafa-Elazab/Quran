package com.mostafa.quran.domain.usecase



import com.mostafa.quran.domain.repository.HomeRepository
import javax.inject.Inject

class GetPrayTimeUseCase @Inject constructor(private val repository: HomeRepository) {

    suspend operator fun invoke(latitude: Double, longitude: Double, month: Int, year: Int) =

        repository.getPrayTime(
            latitude = latitude,
            longitude = longitude,
            month = month,
            year = year
        )




}