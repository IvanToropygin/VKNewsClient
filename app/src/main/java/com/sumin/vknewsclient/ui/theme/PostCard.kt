package com.sumin.vknewsclient.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumin.vknewsclient.R

@Composable
fun PostCard() {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(70.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                painter = painterResource(R.drawable.post_comunity_thumbnail),
                contentDescription = "Group icon"
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Уволено",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "14:00",
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
        Text(
            modifier = Modifier.padding(8.dp),
            text = "кабаныч, когда узнал, что если сотрудникам не платить" +
                    " они начинают умирать от голода"
        )
        Image(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            painter = painterResource(R.drawable.post_content_image),
            contentDescription = "Content image"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                CountAndIcon(
                    count = "206",
                    iconResId = R.drawable.ic_views_count,
                    contentDescription = "view count icon"
                )
            }
            Row {
                CountAndIcon(
                    count = "206",
                    iconResId = R.drawable.ic_share,
                    contentDescription = "share button icon"
                )
                CountAndIcon(
                    count = "11",
                    iconResId = R.drawable.ic_comment,
                    contentDescription = "comment count icon"
                )
                CountAndIcon(
                    count = "491",
                    iconResId = R.drawable.ic_like,
                    contentDescription = "like button  icon"
                )
            }
        }
    }
}

@Composable
private fun CountAndIcon(count: String, iconResId: Int, contentDescription: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = count,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background),
            painter = painterResource(iconResId),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview
@Composable
fun PreviewCardLight() {
    VKNewsClientTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        PostCard()
    }
}

@Preview
@Composable
fun PreviewCardDark() {
    VKNewsClientTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        PostCard()
    }
}