package com.sumin.vknewsclient.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sumin.vknewsclient.domain.FeedPost

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    newsFeedScreenContent: @Composable () -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        homeScreenNavGraph(
            newsFeedScreenContent = newsFeedScreenContent,
            commentsScreenContent = commentsScreenContent
        )
        composable(route = Screen.Favourite.route) {
            favouriteScreenContent()
        }
        composable(route = Screen.Profile.route) {
            profileScreenContent()
        }
    }
}