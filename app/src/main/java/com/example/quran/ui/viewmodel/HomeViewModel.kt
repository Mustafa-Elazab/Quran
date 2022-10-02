package com.example.quran.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quran.data.repository.HomeRepository
import com.example.quran.model.aladhan.AladhanResponse
import com.example.quran.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {


    private val _prayTime: MutableStateFlow<State<AladhanResponse>> =
        MutableStateFlow(State.loading())

    val prayTime: StateFlow<State<AladhanResponse>> = _prayTime


    init {
        getPrayTime()
    }

     fun getPrayTime() {

        viewModelScope.launch {
            repository.getPrayTime()
                .map { resource ->
                    State.fromResource(resource)

                }.collect { state ->
                    _prayTime.value = state

                }
        }
    }


}
