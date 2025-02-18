package com.sumin.vknewsclient.data.repository

import android.app.Application
import android.util.Log
import com.sumin.vknewsclient.data.mappers.NewsFeedMapper
import com.sumin.vknewsclient.data.network.ApiFactory
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.PostComment
import com.sumin.vknewsclient.domain.StatisticItem
import com.sumin.vknewsclient.domain.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepository(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost> get() = _feedPosts.toList()

    private var nextFrom: String? = null

    suspend fun loadRecommendations(): List<FeedPost> {
        val startFrom: String? = nextFrom
        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts

        val response = if (startFrom == null) apiService.loadRecommendations(getAccessToken())
        else {
            apiService.loadRecommendations(
                token = getAccessToken(),
                startFrom = startFrom
            )
        }
        nextFrom = response.newsFeedContentDto.nextFrom
        val posts = mapper.mapResponseToPost(response)
        _feedPosts.addAll(posts)
        return feedPosts
    }

    suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
    }

    suspend fun getComments(feedPost: FeedPost): List<PostComment> {
        val comments = apiService.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        Log.d("NewsFeedRepository", "$comments")
        return mapper.mapResponseToComments(comments)
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (!feedPost.isLiked) {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }

        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(
                StatisticItem(
                    type = StatisticType.LIKES,
                    count = newLikesCount
                )
            )
        }
        val newPost = feedPost.copy(
            statistics = newStatistics,
            isLiked = !feedPost.isLiked
        )
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }

    private fun getAccessToken() = token?.accessToken ?: throw RuntimeException("Token is null")
}