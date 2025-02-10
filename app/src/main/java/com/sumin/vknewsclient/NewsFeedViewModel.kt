package com.sumin.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.StatisticItem
import com.sumin.vknewsclient.ui.theme.NewsFeedScreenState

class NewsFeedViewModel : ViewModel() {

    private val sourceList = mutableListOf<FeedPost>()
        .apply { repeat(10) { add(FeedPost(id = it)) } }

    private val initialState = NewsFeedScreenState.Posts(posts = sourceList)

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> get() = _screenState

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