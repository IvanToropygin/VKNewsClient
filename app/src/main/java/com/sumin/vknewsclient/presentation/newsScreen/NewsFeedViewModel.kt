package com.sumin.vknewsclient.presentation.newsScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.data.mappers.NewsFeedMapper
import com.sumin.vknewsclient.data.network.ApiFactory
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.StatisticItem
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> get() = _screenState

    private val newsFeedMapper = NewsFeedMapper()

    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            val storage = VKPreferencesKeyValueStorage(getApplication())
            val token = VKAccessToken.restore(storage) ?: return@launch
            val response = ApiFactory.apiService.loadRecommendations(token = token.accessToken)
            val feedPosts = newsFeedMapper.mapResponseToPost(response)
            _screenState.value = NewsFeedScreenState.Posts(
                posts = feedPosts
            )
        }
    }

    fun updateCount(
        feedPost: FeedPost,
        item: StatisticItem,
    ) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        val oldStats = feedPost.statistics
        val newStats = oldStats.toMutableList().apply {
            replaceAll { statsItem ->
                if (statsItem.type == item.type) statsItem.copy(count = statsItem.count + 1)
                else statsItem
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStats)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) newFeedPost else it
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }

    fun remove(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = oldPosts)
    }
}