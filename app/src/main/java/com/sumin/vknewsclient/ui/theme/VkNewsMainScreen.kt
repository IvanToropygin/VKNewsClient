package com.sumin.vknewsclient.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sumin.vknewsclient.MainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {

    Scaffold(
        bottomBar = {
            NavigationBar {
                var selectedItemPosition = rememberSaveable { mutableStateOf(0) }

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.value == index,
                        onClick = { selectedItemPosition.value = index },
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
        }
    ) {
        val feedPosts = viewModel.feedPosts.observeAsState(listOf())
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 72.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = feedPosts.value,
                key = { post -> post.id },
            ) { post ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { value ->
                        val isDismissed = value in setOf(
                            SwipeToDismissBoxValue.EndToStart
                        )
                        if (isDismissed) {
                            viewModel.remove(post)
                        }
                        return@rememberSwipeToDismissBoxState isDismissed// Возвращает результат, подтверждающий, что свайп выполнен
                    }
                )
                SwipeToDismissBox(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    enableDismissFromEndToStart = true,//для свайпа справа налево
                    enableDismissFromStartToEnd = false,//для запрета свайпа слева направо
                    backgroundContent = {},
                ) {
                    PostCard(
                        feedPost = post,
                        onViewsClickListener = { statisticItem ->
                            viewModel.updateCount(
                                feedPost = post,
                                item = statisticItem
                            )
                        },
                        onShareClickListener = { statisticItem ->
                            viewModel.updateCount(
                                feedPost = post,
                                item = statisticItem
                            )
                        },
                        onCommentClickListener = { statisticItem ->
                            viewModel.updateCount(
                                feedPost = post,
                                item = statisticItem
                            )
                        },
                        onLikeClickListener = { statisticItem ->
                            viewModel.updateCount(
                                feedPost = post,
                                item = statisticItem
                            )
                        },
                    )
                }
            }
        }
    }
}