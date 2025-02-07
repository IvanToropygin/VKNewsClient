package com.sumin.vknewsclient.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumin.vknewsclient.R
import com.sumin.vknewsclient.ui.theme.domain.FeedPost
import com.sumin.vknewsclient.ui.theme.domain.StatisticItem
import com.sumin.vknewsclient.ui.theme.domain.StatisticType

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onStatisticsItemClickListener: (StatisticItem) -> Unit,
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

        Image(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 8.dp),
            painter = painterResource(id = feedPost.contentImageResId),
            contentDescription = "Content image",
            contentScale = ContentScale.Crop
        )

        Statistics(
            statistics = feedPost.statistics,
            onItemClickListener = onStatisticsItemClickListener
        )
    }
}

@Composable
private fun PostHeader(feedPost: FeedPost) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(70.dp)
                .padding(8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background),
            painter = painterResource(feedPost.avatarResId),
            contentDescription = "Group icon"
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = feedPost.communityName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = feedPost.publicationDate,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "More button",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onItemClickListener: (StatisticItem) -> Unit,
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
                count = viewsItem.count.toString(),
                iconResId = R.drawable.ic_views_count,
                contentDescription = "view count icon",
                onItemClickListener = { onItemClickListener(viewsItem) }
            )
        }
        Row(
            Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                count = sharesItem.count.toString(),
                iconResId = R.drawable.ic_share,
                contentDescription = "share button icon",
                onItemClickListener = { onItemClickListener(sharesItem) }
            )
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                count = commentsItem.count.toString(),
                iconResId = R.drawable.ic_comment,
                contentDescription = "comment count icon",
                onItemClickListener = { onItemClickListener(commentsItem) }
            )
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                count = likesItem.count.toString(),
                iconResId = R.drawable.ic_like,
                contentDescription = "like button icon",
                onItemClickListener = { onItemClickListener(likesItem) }
            )
        }
    }
}

fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException("cannot find type: $type")
}

@Composable
private fun IconWithText(
    iconResId: Int,
    count: String,
    contentDescription: String,
    onItemClickListener: () -> Unit,
) {
    Row(
        modifier = Modifier.clickable {
            onItemClickListener()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = count)
        Spacer(Modifier.width(4.dp))
        Icon(
            painter = painterResource(iconResId),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}