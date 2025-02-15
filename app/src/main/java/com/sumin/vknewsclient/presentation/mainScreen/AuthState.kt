package com.sumin.vknewsclient.presentation.mainScreen

sealed class AuthState {

    object Authorized: AuthState()
    object NotAuthorized: AuthState()
    object Initial: AuthState()
}