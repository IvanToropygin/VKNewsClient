package com.sumin.vknewsclient.ui.theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumin.vknewsclient.MainViewModel
import com.sumin.vknewsclient.domain.FeedPost

@Composable
fun HomeScreen(viewModel: MainViewModel, paddingValues: PaddingValues) {

    val screenState = viewModel.screenState.observeAsState(HomeScreenState.Initial)

    when (val currentState = screenState.value) {
        is HomeScreenState.Posts -> FeedPosts(
            posts = currentState.posts,
            viewModel = viewModel,
            paddingValues = paddingValues
        )

        is HomeScreenState.Comments -> {
            CommentsScreen(
                feedPost = currentState.feedPost,
                comments = currentState.comments,
                onBackPressed = { viewModel.closeComments() }
            )
            BackHandler { viewModel.closeComments() }
        }

        HomeScreenState.Initial -> { }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    posts: List<FeedPost>,
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 72.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = posts,
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
                    onViewsClickListener = { viewModel.updateCount(feedPost = post, item = it) },
                    onShareClickListener = { viewModel.updateCount(feedPost = post, item = it) },
                    onCommentClickListener = { viewModel.showComments(feedPost = post) },
                    onLikeClickListener = { viewModel.updateCount(feedPost = post, item = it) }
                )
            }
        }
    }
}