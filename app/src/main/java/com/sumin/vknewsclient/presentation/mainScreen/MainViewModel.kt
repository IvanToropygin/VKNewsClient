package com.sumin.vknewsclient.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.domain.usecases.CheckAuthStateUseCase
import com.sumin.vknewsclient.domain.usecases.GetAuthStateUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
) : ViewModel() {

    val authState = getAuthStateUseCase()

    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase
        }
    }
}