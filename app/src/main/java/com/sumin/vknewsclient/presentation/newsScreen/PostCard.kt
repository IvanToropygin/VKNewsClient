package com.sumin.vknewsclient.presentation.newsScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sumin.vknewsclient.R
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.StatisticItem
import com.sumin.vknewsclient.domain.StatisticType
import com.sumin.vknewsclient.ui.theme.DarkRed
import java.util.Locale

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onViewsClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onLikeClickListener: (StatisticItem) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground)
    ) {
        PostHeader(feedPost)
        Text(
            modifier = Modifier.padding(8.dp),
            text = feedPost.content
        )
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = feedPost.contentImageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            contentDescription = stringResource(R.string.content_image),
            contentScale = ContentScale.FillWidth
        )

        Statistics(
            statistics = feedPost.statistics,
            onViewsClickListener = onViewsClickListener,
            onShareClickListener = onShareClickListener,
            onCommentClickListener = onCommentClickListener,
            onLikeClickListener = onLikeClickListener,
            isFavourite = feedPost.isLiked
        )
    }
}

@Composable
private fun PostHeader(feedPost: FeedPost) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = feedPost.communityImageUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentDescription = "Group icon"
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = feedPost.communityName,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = feedPost.publicationDate,
                fontWeight = FontWeight.Bold,
            )
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = stringResource(R.string.more_button),
        )
    }
}

@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onViewsClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onLikeClickListener: (StatisticItem) -> Unit,
    isFavourite: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                count = formatStatisticCount(viewsItem.count),
                iconResId = R.drawable.ic_views_count,
                contentDescription = "view count icon",
                onItemClickListener = { onViewsClickListener(viewsItem) }
            )
        }
        Row(
            Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                count = formatStatisticCount(sharesItem.count),
                iconResId = R.drawable.ic_share,
                contentDescription = "share button icon",
                onItemClickListener = { onShareClickListener(sharesItem) }
            )
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                count = formatStatisticCount(commentsItem.count),
                iconResId = R.drawable.ic_comment,
                contentDescription = "comment count icon",
                onItemClickListener = { onCommentClickListener(commentsItem) }
            )
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                count = formatStatisticCount(likesItem.count),
                iconResId = if (isFavourite) R.drawable.ic_like_fill else R.drawable.ic_like,
                contentDescription = "like button icon",
                onItemClickListener = { onLikeClickListener(likesItem) },
                tint = if (isFavourite) DarkRed else MaterialTheme.colorScheme.secondary
            )
        }
    }
}

fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException("cannot find type: $type")
}

private fun formatStatisticCount(count: Int): String {
    return if (count > 1_000_000) String.format("%sM", (count / 1_000_000))
    else if (count > 100_000) String.format("%sK", (count / 1_000))
    else if (count > 1_000) String.format(Locale.getDefault(), "%.1fK", (count / 1_000f))
    else count.toString()
}

@Composable
private fun IconWithText(
    iconResId: Int,
    count: String,
    contentDescription: String,
    onItemClickListener: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.secondary
) {
    Row(
        modifier = Modifier.clickable { onItemClickListener() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(iconResId),
            contentDescription = contentDescription,
            tint = tint
        )
        Spacer(Modifier.width(4.dp))
        Text(text = count)
    }
}