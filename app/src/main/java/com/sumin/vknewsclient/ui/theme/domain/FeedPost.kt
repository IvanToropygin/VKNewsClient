package com.sumin.vknewsclient.ui.theme.domain

import com.sumin.vknewsclient.R

data class FeedPost(
    val id: Int = 0,
    val communityName: String = "/dev/null",
    val publicationDate: String = "13:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val content: String = "кабаныч, когда узнал, что если сотрудникам не платить они начинают умирать от голода",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 10),
        StatisticItem(StatisticType.SHARES, 11),
        StatisticItem(StatisticType.COMMENTS, 12),
        StatisticItem(StatisticType.LIKES, 13),
    ),
)
