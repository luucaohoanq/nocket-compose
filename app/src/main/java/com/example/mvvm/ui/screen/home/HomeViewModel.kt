package com.example.mvvm.ui.screen.home

import androidx.lifecycle.ViewModel
import com.example.mvvm.repositories.MainLog
import com.example.mvvm.repositories.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class HomeUiState (
    val test: String
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val log: MainLog?,
    private val store: Store?
) : ViewModel() {
    val _uiState = MutableStateFlow(HomeUiState(""))
    val uiState = _uiState.asStateFlow()

    fun updateTest(s: String) {
        _uiState.value = _uiState.value.copy(test = s)
    }

    override fun onCleared() {
        log?.i("HomeViewModel", "onCleared ${store?.getValue("test")}.")
    }
}