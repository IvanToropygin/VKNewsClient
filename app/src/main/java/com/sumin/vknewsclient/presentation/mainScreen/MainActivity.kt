package com.sumin.vknewsclient.presentation.mainScreen

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.ui.theme.VKNewsClientTheme
import com.vk.api.sdk.VK.getVKAuthActivityResultContract
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VKNewsClientTheme {
                val viewModel: MainViewModel = viewModel()
                val authState = viewModel.authState.observeAsState(AuthState.Initial)
                val authLauncher = rememberLauncherForActivityResult(
                    contract = getVKAuthActivityResultContract()
                ) { viewModel.performAuthResult(it) }

                when (authState.value) {
                    is AuthState.Initial -> {}
                    is AuthState.Authorized -> MainScreen()
                    is AuthState.NotAuthorized -> LoginScreen{
                        authLauncher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                    }
                }
            }
        }
    }
}