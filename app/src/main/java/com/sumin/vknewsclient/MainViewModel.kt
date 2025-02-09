package com.sumin.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.ui.theme.domain.FeedPost
import com.sumin.vknewsclient.ui.theme.domain.StatisticItem

class MainViewModel : ViewModel() {

    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(10) { add(FeedPost(id = it)) }
    }

    private val _feedPosts = MutableLiveData<List<FeedPost>>(sourceList)
    val feedPosts: LiveData<List<FeedPost>> get() = _feedPosts

    fun updateCount(
        feedPost: FeedPost,
        item: StatisticItem,
    ) {
        val oldPosts = feedPosts.value?.toMutableList() ?: mutableListOf()
        val oldStats = feedPost.statistics
        val newStats = oldStats.toMutableList().apply {
            replaceAll { statsItem ->
                if (statsItem.type == item.type) statsItem.copy(count = statsItem.count + 1)
                else statsItem
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStats)
        _feedPosts.value = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) newFeedPost else it
            }
        }
    }

    fun remove(feedPost: FeedPost) {
        val oldPosts = feedPosts.value?.toMutableList() ?: mutableListOf()
        oldPosts.remove(feedPost)
        _feedPosts.value = oldPosts
    }
}