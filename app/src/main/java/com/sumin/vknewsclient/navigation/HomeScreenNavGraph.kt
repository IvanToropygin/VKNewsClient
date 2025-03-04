package com.sumin.vknewsclient.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.sumin.vknewsclient.domain.entity.FeedPost
import com.sumin.vknewsclient.navigation.Screen.Companion.KEY_FEED_POST

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit,
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(route = Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(KEY_FEED_POST) { type = FeedPost.NavigationType }
            )
        ) {
            val feedPost = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.arguments?.getParcelable(KEY_FEED_POST, FeedPost::class.java)
                    ?: throw RuntimeException("Args is null")
            } else {
                it.arguments?.getParcelable(KEY_FEED_POST)?: throw RuntimeException("Args is null")
            }
            commentsScreenContent(feedPost)
        }
    }
}