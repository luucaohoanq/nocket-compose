package com.example.mvvm.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.example.mvvm.repositories.MainLog
import com.example.mvvm.repositories.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val log: MainLog,
    private val store: Store
) : ViewModel() {
    override fun onCleared() {
        log.i("DetailViewModel", "onCleared ${store.getValue("test")}.")
    }
}