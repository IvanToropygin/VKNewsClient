package com.sumin.vknewsclient.data.models

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: String,
    @SerializedName("sourceId") val communityId: Long,
    @SerializedName("isFavourite") val isFavourite: Boolean,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("views") val views: ViewsDto,
    @SerializedName("reposts") val reposts: RepostsDto,
    @SerializedName("attachments") val attachments: List<AttachmentDto>?,
)
