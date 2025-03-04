package com.sumin.vknewsclient.presentation.mainScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.domain.entity.AuthState
import com.sumin.vknewsclient.presentation.getApplicationComponent
import com.sumin.vknewsclient.ui.theme.VKNewsClientTheme
import com.vk.api.sdk.VK.getVKAuthActivityResultContract
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val component = getApplicationComponent()
            val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())
            val authState = viewModel.authState.collectAsState(AuthState.Initial)
            val authLauncher = rememberLauncherForActivityResult(
                contract = getVKAuthActivityResultContract()
            ) { viewModel.performAuthResult() }

            VKNewsClientTheme {
                when (authState.value) {
                    is AuthState.Initial -> {}
                    is AuthState.Authorized -> MainScreen()
                    is AuthState.NotAuthorized -> LoginScreen {
                        authLauncher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                    }
                }
            }
        }
    }
}