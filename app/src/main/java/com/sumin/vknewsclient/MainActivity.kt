package com.sumin.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sumin.vknewsclient.ui.theme.MainScreen
import com.sumin.vknewsclient.ui.theme.VKNewsClientTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VKNewsClientTheme { MainScreen(
                viewModel = viewModel
            ) }
        }
    }
}