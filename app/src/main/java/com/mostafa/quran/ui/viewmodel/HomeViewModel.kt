package com.mostafa.quran.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafa.quran.data.model.BaseResponse
import com.mostafa.quran.data.remote.response.NetworkResponse
import com.mostafa.quran.domain.model.aladhan.AladhanResponse
import com.mostafa.quran.domain.usecase.GetPrayTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetPrayTimeUseCase) : ViewModel() {


    private val _state: MutableStateFlow<NetworkResponse<AladhanResponse, BaseResponse>?> =
        MutableStateFlow<NetworkResponse<AladhanResponse, BaseResponse>?>(null)
    val state get() = _state.asStateFlow()



    fun getPrayTime() {

        viewModelScope.launch {
            useCase.getPrayTime().collect {
                _state.value = it
            }
        }
    }


}







