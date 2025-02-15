package com.sumin.vknewsclient.presentation.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sumin.vknewsclient.navigation.AppNavGraph
import com.sumin.vknewsclient.navigation.rememberNavigationState
import com.sumin.vknewsclient.presentation.commentsScreen.CommentsScreen
import com.sumin.vknewsclient.presentation.newsScreen.NewsFeedScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainScreen() {

    val navigationState = rememberNavigationState()

    Scaffold(bottomBar = {
        NavigationBar {
            val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
            val items = listOf(
                NavigationItem.Home,
                NavigationItem.Favourite,
                NavigationItem.Profile
            )
            items.forEach { item ->
                val selected = navBackStackEntry?.destination?.hierarchy?.any {
                    it.route == item.screen.route
                } ?: false
                NavigationBarItem(
                    selected = selected,
                    onClick = { if (!selected) navigationState.navigateTo(item.screen.route) },
                    icon = { Icon(item.icon, contentDescription = null) },
                    label = { Text(text = stringResource(id = item.titleResId)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.secondary,
                        unselectedTextColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
    }) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                NewsFeedScreen(
                    paddingValues = paddingValues,
                    onCommentsClickListener = { feedPost ->
                        navigationState.navigateToComments(feedPost)
                    }
                )
            },
            commentsScreenContent = { feedPost ->
                CommentsScreen(
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                    feedPost = feedPost
                )
            },
            favouriteScreenContent = { TextCounter("Favourite") },
            profileScreenContent = { TextCounter("Profile") })
    }
}

@Composable
private fun TextCounter(name: String) {
    var count by rememberSaveable { mutableStateOf(0) }
    Text(
        modifier = Modifier.clickable { count++ }, text = "$name: $count", color = Color.Black
    )
}