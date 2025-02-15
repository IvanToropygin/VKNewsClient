package com.sumin.vknewsclient

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.sumin.vknewsclient.ui.theme.VKNewsClientTheme
import com.vk.api.sdk.VK.getVKAuthActivityResultContract
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VKNewsClientTheme {
                val someState = remember { mutableStateOf(true) }
                Log.d("MainActivity123", "Recomposition: ${someState.value}")
                val authLauncher =
                    rememberLauncherForActivityResult(contract = getVKAuthActivityResultContract()) { result: VKAuthenticationResult ->
                        when (result) {
                            is VKAuthenticationResult.Success -> {
                                Log.d("MainActivity123", "VKAuthenticationResult.Success")
                            }

                            is VKAuthenticationResult.Failed -> {
                                Log.d("MainActivity123", "VKAuthenticationResult.Failed")
                            }
                        }
                    }
                SideEffect {//вызывается только при каждой композиции
                    Log.d("MainActivity123", "SideEffect")
                }

                LaunchedEffect(//если в качестве ключа указать чам стейт - будет вызываться при каждой композиции
                    key1 = someState.value) {//вызывается при первой композиции (из-за стейта, при переворачивании экрана тоже будет вызов)
                    Log.d("MainActivity123", "LaunchedEffect")
//                    authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS))
                }
                Button(
                    onClick = { someState.value = !someState.value }) {
                    Text(text = "Change state")
                }
            }
        }
    }
}