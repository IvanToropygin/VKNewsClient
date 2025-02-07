package com.sumin.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.ui.theme.domain.FeedPost
import com.sumin.vknewsclient.ui.theme.domain.StatisticItem

class MainViewModel: ViewModel() {

    private val _feedPost = MutableLiveData(FeedPost())
    val feedPost : LiveData<FeedPost> get() = _feedPost

    fun updateCount(item: StatisticItem) {
        val oldStats = feedPost.value?.statistics ?: throw IllegalStateException("_feedPost == null")
        val newStats = oldStats.toMutableList().apply {
            replaceAll { statsItem ->
                if (statsItem.type == item.type)
                    statsItem.copy(count = statsItem.count + 1)
                else statsItem
            }
        }
        _feedPost.value = feedPost.value?.copy(statistics = newStats)
    }
}