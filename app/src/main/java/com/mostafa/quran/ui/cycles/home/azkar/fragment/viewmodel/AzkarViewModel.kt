package com.mostafa.quran.ui.cycles.home.azkar.fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafa.quran.domain.model.azkar.AzkarResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AzkarViewModel @Inject constructor() : ViewModel() {


    private val _searchResults = MutableSharedFlow<List<AzkarResponseItem>>()
    val searchResults get() = _searchResults.asSharedFlow()

    private val _validationState = MutableSharedFlow<ValidationErrors>()
    val validationState get() = _validationState.asSharedFlow()


    private fun searchItems(query: String, items: List<AzkarResponseItem>) {
        viewModelScope.launch {
            val results = items.filter { item ->
                item.category.contains(query, ignoreCase = true) ||
                        item.array.any { subItem ->
                            subItem.text.contains(query, ignoreCase = true)
                        }
            }
            _searchResults.emit(results)
        }
    }

    fun validateText(text: String, items: List<AzkarResponseItem>) {
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
