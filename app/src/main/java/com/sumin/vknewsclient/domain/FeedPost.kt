package com.sumin.vknewsclient.domain

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.google.gson.Gson
import com.sumin.vknewsclient.R
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable {
    companion object {

        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false) {
            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            override fun get(bundle: Bundle, key: String): FeedPost? {
                return bundle.getParcelable(key, FeedPost::class.java)
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson(value, FeedPost::class.java)
            }

            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
