package com.mostafa.quran.ui.cycles.home.quran.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafa.quran.data.remote.dto.ErrorResponse
import com.mostafa.quran.data.remote.dto.SurahAudioDTO
import com.mostafa.quran.data.remote.response.NetworkResponse
import com.mostafa.quran.domain.model.ChapterX
import com.mostafa.quran.domain.usecase.GetQuranAudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuranViewModel @Inject constructor(private val useCase: GetQuranAudioUseCase) : ViewModel() {

    private val _quranAudioState =
        MutableStateFlow<NetworkResponse<SurahAudioDTO, ErrorResponse>?>(null)

    val quranAudioState get() = _quranAudioState.asStateFlow()

    private val _searchResults = MutableSharedFlow<List<ChapterX>>()
    val searchResults get() = _searchResults.asSharedFlow()

    private val _validationState = MutableSharedFlow<ValidationErrors>()
    val validationState get() = _validationState.asSharedFlow()


      fun playAudio(surah_id: Int) {
       viewModelScope.launch {
           useCase.invoke(surah_id = surah_id).collect {
               _quranAudioState.emit(it)
           }
       }
    }


    private fun searchItems(query: String, items: List<ChapterX>) {
        viewModelScope.launch {
            val filteredChapters = items.filter { chapter ->
                chapter.name.contains(query, ignoreCase = true) || chapter.English_Name.contains(
                    query,
                    ignoreCase = true
                )
            }
            _searchResults.emit(filteredChapters)
        }
    }

    fun validateText(text: String, items: List<ChapterX>) {
        viewModelScope.launch {
            if (text.isEmpty()) {
                _validationState.emit(ValidationErrors.TEXT)
            } else {
                searchItems(text, items)

            }
        }
    }
}

enum class ValidationErrors {
    TEXT
}
