package com.sumin.vknewsclient.domain

import com.sumin.vknewsclient.R

data class PostComment(
    val id: Int,
    val authorName: String = "author",
    val authorAvatarId: Int = R.drawable.comment_author_avatar,
    val commentText: String = "Long comment text",
    val publicationDate: String = "15:00"
)
