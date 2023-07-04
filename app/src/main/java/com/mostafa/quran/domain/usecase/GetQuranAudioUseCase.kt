package com.mostafa.quran.domain.usecase

import com.mostafa.quran.domain.repository.QuranRepository
import javax.inject.Inject

class GetQuranAudioUseCase @Inject constructor(private val repository: QuranRepository) {

    suspend operator fun invoke(surah_id: Int) = repository.getSurahAudio(surah_id)
}