package com.sumin.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.PostComment
import com.sumin.vknewsclient.domain.StatisticItem
import com.sumin.vknewsclient.ui.theme.HomeScreenState

class MainViewModel : ViewModel() {

    private val comments = mutableListOf<PostComment>()
        .apply { repeat(20) { add(PostComment(id = it))} }

    private val sourceList = mutableListOf<FeedPost>()
        .apply { repeat(10) { add(FeedPost(id = it)) } }

    private val initialState = HomeScreenState.Posts(posts = sourceList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> get() = _screenState

    private var savedState: HomeScreenState? = initialState

    fun showComments(feedPost: FeedPost) {
        savedState = _screenState.value
        _screenState.value = HomeScreenState.Comments(feedPost = feedPost,comments = comments)
    }

    fun closeComments() {
        _screenState.value = savedState
    }

    fun updateCount(
        feedPost: FeedPost,
        item: StatisticItem,
    ) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

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
        _screenState.value = HomeScreenState.Posts(posts = newPosts)
    }

    fun remove(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = HomeScreenState.Posts(posts = oldPosts)
    }
}