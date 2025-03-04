package com.sumin.vknewsclient.presentation.newsScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.domain.entity.FeedPost
import com.sumin.vknewsclient.presentation.getApplicationComponent
import com.sumin.vknewsclient.ui.theme.DarkBlue

@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit,
) {
    val component = getApplicationComponent()
    val viewModel: NewsFeedViewModel = viewModel(factory = component.getViewModelFactory())

    val screenState = viewModel.screenState.collectAsState(NewsFeedScreenState.Initial)

    NewsFeedScreenContent(
        screenState = screenState,
        paddingValues = paddingValues,
        onCommentsClickListener = onCommentsClickListener,
        viewModel = viewModel
    )
}

@Composable
private fun NewsFeedScreenContent(
    screenState: State<NewsFeedScreenState>,
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit,
    viewModel: NewsFeedViewModel,
) {
    when (val currentState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                posts = currentState.posts,
                viewModel = viewModel,
                paddingValues = paddingValues,
                onCommentsClickListener = onCommentsClickListener,
                nextDataIsLoading = currentState.nextDataIsLoading
            )
        }

        NewsFeedScreenState.Initial -> {}
        NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    posts: List<FeedPost>,
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit,
    nextDataIsLoading: Boolean,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 16.dp),
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
                    onCommentClickListener = { onCommentsClickListener(post) },
                    onLikeClickListener = { viewModel.changeLikeStatus(post) }
                )
            }
        }
        item {
            if (nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            } else SideEffect { viewModel.loadNextRecommendations() }
        }
    }
}