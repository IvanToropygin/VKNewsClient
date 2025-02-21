package com.sumin.vknewsclient.presentation.newsScreen

import com.sumin.vknewsclient.domain.entity.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    object Loading : NewsFeedScreenState()

    data class Posts(
        val posts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false,
    ) : NewsFeedScreenState()
}