package com.sumin.vknewsclient.presentation.mainScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.domain.entity.AuthState
import com.sumin.vknewsclient.presentation.NewsFeedApplication
import com.sumin.vknewsclient.presentation.ViewModelFactory
import com.sumin.vknewsclient.ui.theme.VKNewsClientTheme
import com.vk.api.sdk.VK.getVKAuthActivityResultContract
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as NewsFeedApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            VKNewsClientTheme {
                val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
                val authState = viewModel.authState.collectAsState(AuthState.Initial)
                val authLauncher = rememberLauncherForActivityResult(
                    contract = getVKAuthActivityResultContract()
                ) { viewModel.performAuthResult() }

                when (authState.value) {
                    is AuthState.Initial -> {}
                    is AuthState.Authorized -> MainScreen(viewModelFactory)
                    is AuthState.NotAuthorized -> LoginScreen {
                        authLauncher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                    }
                }
            }
        }
    }
}