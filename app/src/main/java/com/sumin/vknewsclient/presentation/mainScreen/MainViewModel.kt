package com.sumin.vknewsclient.presentation.mainScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.sumin.vknewsclient.domain.usecases.CheckAuthStateUseCase
import com.sumin.vknewsclient.domain.usecases.GetAuthStateUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getAuthStateUseCase = GetAuthStateUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val authState = getAuthStateUseCase()

    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase
        }
    }
}