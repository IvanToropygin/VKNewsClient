package com.sumin.vknewsclient.domain.usecases

import com.sumin.vknewsclient.domain.entity.FeedPost
import com.sumin.vknewsclient.domain.entity.PostComment
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetCommentsUseCase(
    private val repository: NewsFeedRepository,
) {
    operator fun invoke(feedPost: FeedPost): StateFlow<List<PostComment>> =
        repository.getComments(feedPost)
}