package com.sumin.vknewsclient.domain.usecases

import com.sumin.vknewsclient.domain.entity.AuthState
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateUseCase(
    private val repository: NewsFeedRepository,
) {
    operator fun invoke(): StateFlow<AuthState> = repository.getAuthStateFlow()
}